package com.example.movieapp.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieapp.Model.Result;
import com.example.movieapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private List<Result> movieList;
    private MoviesAdapter adapter;
    private Button submitButton;
    private EditText yearEditor;
    private MainActivityViewModel viewModel;
    private RecyclerView movieRecyclerView;
    private GestureDetector gestureDetector;

    public static final String MOVIE_DETAILS = "MovieDetails";
    public static final int MOVIE_DETAIL_CODE = 5028;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        initViewModel();
        initEditTextAndSubmitButton();
        initRecyclerView();
}

    private void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        viewModel.getMovies().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                movieList = results;
                updateUI();
            }
        });
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void initEditTextAndSubmitButton(){
        yearEditor = findViewById(R.id.yearValue);

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(yearEditor.getText().toString()) && TextUtils.isDigitsOnly(yearEditor.getText().toString()))
                    viewModel.getMoviesFromYear(yearEditor.getText().toString());
                else
                    Toast.makeText(MainActivity.this, getString(R.string.yearEditorError), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initRecyclerView(){
        adapter = new MoviesAdapter(movieList);
        movieRecyclerView = findViewById(R.id.moviesRecyclerView);
        movieRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        movieRecyclerView.setAdapter(adapter);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e){
                return true;
            }
        });

        movieRecyclerView.addOnItemTouchListener(this);
    }

    private void updateUI(){
        if (adapter == null){
            adapter = new MoviesAdapter(movieList);
            movieRecyclerView.setAdapter(adapter);
        }
        else
            adapter.updateMovieList(movieList);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null){
            int adapterPosition = rv.getChildAdapterPosition(child);
            if (gestureDetector.onTouchEvent(e)){
                Intent intent = new Intent(MainActivity.this, MovieDetails.class);
                intent.putExtra(MOVIE_DETAILS, movieList.get(adapterPosition));
                startActivityForResult(intent, MOVIE_DETAIL_CODE);
            }
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MOVIE_DETAIL_CODE && resultCode == RESULT_OK)
            Toast.makeText(MainActivity.this, getString(R.string.movieDetailError), Toast.LENGTH_LONG).show();
    }
}
