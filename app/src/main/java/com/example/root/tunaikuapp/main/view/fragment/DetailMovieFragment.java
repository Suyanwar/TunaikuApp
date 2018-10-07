package com.example.root.tunaikuapp.main.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.tunaikuapp.MainActivity;
import com.example.root.tunaikuapp.R;
import com.example.root.tunaikuapp.main.MainContract;
import com.example.root.tunaikuapp.main.model.DetailModel;
import com.example.root.tunaikuapp.main.presenter.DetailPresenter;
import com.example.root.tunaikuapp.objects.Movie;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import me.anwarshahriar.calligrapher.Calligrapher;

public class DetailMovieFragment extends Fragment implements MainContract.DetailView {

    public String movie_id = "";
    public MainContract.DetailPresenter detailPresenter;

    ImageView iv_detail_movie_cover;
    TextView tv_movie_rate, tv_detail_movie_title, tv_movie_desc;
    Button btnCTA;
    LinearLayout ll_progress_detail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getContext())).getSupportActionBar()).setTitle("");
        Objects.requireNonNull(((MainActivity) getContext()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setAlpha(255);
        colorDrawable.setColor((Color.parseColor("#01000000")));
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getContext())).getSupportActionBar()).setBackgroundDrawable(colorDrawable);
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getContext())).getSupportActionBar()).setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));

        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont(((MainActivity)getContext()), "Roboto-Regular.ttf", true);

        iv_detail_movie_cover = view.findViewById(R.id.iv_detail_movie_cover);
        tv_detail_movie_title = view.findViewById(R.id.tv_detail_movie_title);
        tv_movie_rate = view.findViewById(R.id.tv_movie_rate);
        tv_movie_desc = view.findViewById(R.id.tv_movie_desc);
        btnCTA = view.findViewById(R.id.btnCTA);
        ll_progress_detail = view.findViewById(R.id.ll_progress_detail);


        Bundle bundle = this.getArguments();
        detailPresenter = new DetailPresenter(this, new DetailModel());
        if (bundle != null) {
            movie_id = bundle.getString("movie_id");
            detailPresenter.requestDataFromServer(movie_id);
        }

        return view;
    }

    @Override
    public void showProgress() {
        ll_progress_detail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        ll_progress_detail.setVisibility(View.GONE);
    }

    @Override
    public void setDataToView(Movie movie) {
        String url = "http://image.tmdb.org/t/p/w780/" + movie.getPoster_path();
        Picasso.get().load(url).centerCrop().fit().into(iv_detail_movie_cover);
        tv_detail_movie_title.setText(movie.getOriginal_title());
        tv_movie_desc.setText(movie.getOverview());
        tv_movie_rate.setText(movie.getVote_average());
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
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
        detailPresenter.onDestroy();
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getContext())).getSupportActionBar()).setTitle("MOVIES");
        Objects.requireNonNull(((MainActivity) getContext()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((MainActivity)Objects.requireNonNull(getContext())).getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
