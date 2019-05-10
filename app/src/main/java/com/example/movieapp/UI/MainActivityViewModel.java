package com.example.movieapp.UI;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.movieapp.API.MovieRepository;
import com.example.movieapp.Model.Movies;
import com.example.movieapp.Model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {

    private MovieRepository movieRepository = new MovieRepository();

    private MutableLiveData<List<Result>> movies = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application){
        super(application);
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<List<Result>> getMovies() {
        return movies;
    };

    public void getMoviesFromYear(String year){
        movieRepository.getMoviesFromYear(year)
                .enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(Call<Movies> call, Response<Movies> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            movies.setValue(response.body().getResults());
                        } else {
                            error.setValue("Api Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Movies> call, Throwable t) {
                        error.setValue("Api Error: " + t.getMessage());
                    }
                });
    }
}
