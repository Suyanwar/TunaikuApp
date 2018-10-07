package com.example.root.tunaikuapp.main.presenter;

import com.example.root.tunaikuapp.main.MainContract;
import com.example.root.tunaikuapp.objects.Movie;

public class DetailPresenter implements MainContract.DetailPresenter, MainContract.DetailModel.OnFinishedListener {
    private MainContract.DetailView detailView;
    private MainContract.DetailModel detailModel;

    public DetailPresenter(MainContract.DetailView detailView, MainContract.DetailModel detailModel) {
        this.detailView = detailView;
        this.detailModel = detailModel;
    }

    @Override
    public void onDestroy() {
        detailView = null;
    }

    @Override
    public void onRefreshButtonClick(String id) {
        if (detailView != null){
            detailView.showProgress();
        }
        detailModel.getDetailMovie(id, this);
    }

    @Override
    public void requestDataFromServer(String id) {
        detailView.showProgress();
        detailModel.getDetailMovie(id, this);
    }

    @Override
    public void onFinished(Movie movie) {
        if(detailView != null){
            detailView.setDataToView(movie);
            detailView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if(detailView != null){
            detailView.onResponseFailure(t);
            detailView.hideProgress();
        }
    }
}
