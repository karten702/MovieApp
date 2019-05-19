package com.example.movieapp.UI;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Model.Result;
import com.example.movieapp.R;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Result> movieList;

    public MoviesAdapter(List<Result> resultList){
        movieList = resultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_cell, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Result movie = movieList.get(i);
        viewHolder.movieTitle.setText(movie.getTitle());
        Glide.with(viewHolder.itemView).load("https://image.tmdb.org/t/p/original" + movie.getPosterPath()).into(viewHolder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void updateMovieList(List<Result> newMovieList){
        movieList = newMovieList;
        if (newMovieList != null)
            this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView movieTitle;
        private ImageView moviePoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            moviePoster = itemView.findViewById(R.id.moviePoster);
        }
    }
}


