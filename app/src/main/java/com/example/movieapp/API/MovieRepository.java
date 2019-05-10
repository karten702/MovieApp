package com.example.movieapp.API;

import com.example.movieapp.BuildConfig;
import com.example.movieapp.Model.Movies;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class MovieRepository {

    private MovieApiService movieApiService = MovieApi.create();

    public Call<Movies> getMoviesFromYear(String releaseYear) {
        Map<String, String> data = new HashMap<>();
        data.put("api_key", BuildConfig.ApiKey);
        data.put("language", "en-US");
        data.put("sort_by", "popularity.desc");
        data.put("include_adult", "false");
        data.put("include_video", "false");
        data.put("page", "1");
        data.put("primary_release_year", releaseYear);

        return movieApiService.getMoviesFromYear(data);
    }
}
