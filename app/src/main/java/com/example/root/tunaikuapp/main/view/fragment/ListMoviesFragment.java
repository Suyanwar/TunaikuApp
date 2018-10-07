package com.example.root.tunaikuapp.main.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.root.tunaikuapp.MainActivity;
import com.example.root.tunaikuapp.R;
import com.example.root.tunaikuapp.main.MainContract;
import com.example.root.tunaikuapp.main.model.MainModel;
import com.example.root.tunaikuapp.main.presenter.MainPresenter;
import com.example.root.tunaikuapp.main.view.adapter.MovieListAdapter;
import com.example.root.tunaikuapp.objects.Movie;

import java.util.ArrayList;
import java.util.Objects;

import me.anwarshahriar.calligrapher.Calligrapher;

public class ListMoviesFragment extends Fragment implements MainContract.MainView, SwipeRefreshLayout.OnRefreshListener  {
    RecyclerView recycle_list_movies;
    SwipeRefreshLayout swipe_list_movies;
    LinearLayout llprogress;

    public String status_param = "popular";

    private MainContract.MainPresenter mainPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_movies, container, false);
        setHasOptionsMenu(true);

        llprogress = view.findViewById(R.id.ll_progress);

        Calligrapher calligrapher = new Calligrapher(Objects.requireNonNull(getContext()));
        calligrapher.setFont(((MainActivity)getContext()), "Roboto-Regular.ttf", true);

        GridLayoutManager movieLayout = new GridLayoutManager(getActivity(), 2);

        swipe_list_movies = view.findViewById(R.id.swipe_list_movies);
        swipe_list_movies.setOnRefreshListener(this);

        recycle_list_movies = view.findViewById(R.id.recycle_list_movies);
        recycle_list_movies.setHasFixedSize(true);
        recycle_list_movies.setLayoutManager(movieLayout);

        mainPresenter = new MainPresenter(this, new MainModel());
        mainPresenter.requestDataFromServer(status_param);

        return view;
    }

    @Override
    public void showProgress() {
        llprogress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        llprogress.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerView(ArrayList<Movie> movieArrayList) {
        swipe_list_movies.setRefreshing(false);
        MovieListAdapter rcAdapter = new MovieListAdapter(getActivity(), movieArrayList);
        recycle_list_movies.setAdapter(rcAdapter);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        swipe_list_movies.setRefreshing(false);
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Network Error");
        alertDialog.setMessage("Unable to contact the server");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainPresenter.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                status_param = "popular";
                break;
            case R.id.action_top_rated:
                status_param = "top_rated";
                break;
            default:
                break;
        }
        mainPresenter.requestDataFromServer(status_param);
        return false;
    }

    @Override
    public void onRefresh() {
        mainPresenter.onRefreshButtonClick(status_param);
    }
}
