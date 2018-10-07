package com.example.root.tunaikuapp.main;

import com.example.root.tunaikuapp.objects.Movie;

import java.util.ArrayList;

public interface MainContract {


    /**
     Presenter
     **/

    interface MainPresenter {

        void onDestroy();

        void onRefreshButtonClick(String param);

        void requestDataFromServer(String param);
    }



    /**
     View
     **/
    interface MainView {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(ArrayList<Movie> movieArrayList);

        void onResponseFailure(Throwable throwable);
    }



    /**
     Model
     **/
    interface MainModel {

        interface OnFinishedListener {
            void onFinished(ArrayList<Movie> movieArrayList);
            void onFailure(Throwable t);
        }

        void getMovieArrayList(String param, OnFinishedListener onFinishedListener);
    }
}
