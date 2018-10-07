package com.example.root.tunaikuapp.main.model;

import android.support.annotation.NonNull;

import com.example.root.tunaikuapp.main.MainContract;
import com.example.root.tunaikuapp.objects.Movie;
import com.example.root.tunaikuapp.utils.Rest_API;
import com.example.root.tunaikuapp.utils.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailModel implements MainContract.DetailModel {
    @Override
    public void getDetailMovie(String id, final OnFinishedListener onFinishedListener) {

        Rest_API service = RetrofitInstance.getRetrofitInstance().create(Rest_API.class);

        Call<Movie> call = service.getMoviesDataDetail(id);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.body() != null) {
                    Movie mv = new Movie(response.body().getId(), response.body().getOriginal_title(), response.body().getPoster_path(), response.body().getVote_average(), response.body().getOverview());
                    onFinishedListener.onFinished(mv);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
