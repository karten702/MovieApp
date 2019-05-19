package com.example.movieapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Model.Result;
import com.example.movieapp.R;

public class MovieDetails extends AppCompatActivity {

    private ImageView backdrop;
    private ImageView posterImage;
    private TextView movieTite;
    private TextView releaseDate;
    private TextView rating;
    private TextView summary;
    private Result movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieTite = findViewById(R.id.movieTitle);
        releaseDate = findViewById(R.id.releaseValue);
        rating = findViewById(R.id.ratingValue);
        summary = findViewById(R.id.overviewValue);
        backdrop = findViewById(R.id.movieBackdrop);
        posterImage = findViewById(R.id.moviePoster);

        movie = getIntent().getParcelableExtra(MainActivity.MOVIE_DETAILS);
        if (movie != null){
            movieTite.setText(movie.getTitle());
            releaseDate.setText(getString(R.string.releaseDateValue) + " " + movie.getReleaseDate());
            rating.setText(String.valueOf(movie.getVoteAverage()));
            summary.setText(movie.getOverview());
            summary.setMovementMethod(new ScrollingMovementMethod());
            Glide.with(this).load("https://image.tmdb.org/t/p/original" + movie.getPosterPath()).into(posterImage);
            Glide.with(this).load("https://image.tmdb.org/t/p/original" + movie.getBackdropPath()).into(backdrop);
        }
        else{
            Intent resultIntent = new Intent();
            resultIntent.putExtra(MainActivity.MOVIE_DETAILS, movie);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }
}
