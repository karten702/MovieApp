package com.example.movieapp.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieapp.Model.Result;
import com.example.movieapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button submitButton;
    private EditText yearEditor;
    private MainActivityViewModel viewModel;
    private TextView devText;
    private ImageView devImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        devText = findViewById(R.id.devText);
        devImage = findViewById(R.id.devImageView);

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getMoviesFromYear("2018");
            }
        });

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        viewModel.getMovies().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                Result firstResult = results.get(0);
                devText.setText(firstResult.getTitle());
                setImage(firstResult.getPosterPath());
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

    public void setImage(String path){
        Glide.with(this).load("https://image.tmdb.org/t/p/original" + path).into(devImage);
    }
}
