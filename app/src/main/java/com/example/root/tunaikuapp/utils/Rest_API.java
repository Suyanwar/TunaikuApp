package com.example.root.tunaikuapp.utils;

import com.example.root.tunaikuapp.objects.Movie;
import com.example.root.tunaikuapp.objects.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Rest_API {

    @GET("{param}?api_key=0cc67b95807d8f15ec17dbcc25df7985")
    Call<MovieList> getMoviesData(@Path("param") String param);

    @GET("{id}?api_key=0cc67b95807d8f15ec17dbcc25df7985")
    Call<Movie> getMoviesDataDetail(@Path("id") String id);

}
