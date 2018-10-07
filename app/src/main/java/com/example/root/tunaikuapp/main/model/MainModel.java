package com.example.root.tunaikuapp.main.model;

import android.support.annotation.NonNull;

import com.example.root.tunaikuapp.main.MainContract;
import com.example.root.tunaikuapp.objects.MovieList;
import com.example.root.tunaikuapp.utils.Rest_API;
import com.example.root.tunaikuapp.utils.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModel implements MainContract.MainModel {
    @Override
    public void getMovieArrayList(String param, final OnFinishedListener onFinishedListener) {

        Rest_API service = RetrofitInstance.getRetrofitInstance().create(Rest_API.class);

        Call<MovieList> call = service.getMoviesData(param);

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
                if (response.body() != null) {
                    onFinishedListener.onFinished(response.body().getMovieArrayList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
