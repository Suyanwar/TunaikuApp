package com.example.root.tunaikuapp.main.presenter;

import com.example.root.tunaikuapp.main.MainContract;
import com.example.root.tunaikuapp.objects.Movie;

import java.util.ArrayList;

public class MainPresenter implements MainContract.MainPresenter, MainContract.MainModel.OnFinishedListener {
    private MainContract.MainView mainView;
    private MainContract.MainModel mainModel;

    public MainPresenter(MainContract.MainView mainView, MainContract.MainModel mainModel){
        this.mainView = mainView;
        this.mainModel = mainModel;
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onRefreshButtonClick(String param) {
        if (mainView != null){
            mainView.showProgress();
        }
        mainModel.getMovieArrayList(param, this);
    }

    @Override
    public void requestDataFromServer(String param) {
        mainView.showProgress();
        mainModel.getMovieArrayList(param, this);
    }

    @Override
    public void onFinished(ArrayList<Movie> movieArrayList) {
        if(mainView != null){
            mainView.setDataToRecyclerView(movieArrayList);
            mainView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if(mainView != null){
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }
}
