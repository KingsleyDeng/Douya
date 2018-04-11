package com.kingsley.douya.presenter;


import com.kingsley.douya.base.BasePresenter;
import com.kingsley.douya.model.MovieModel;
import com.kingsley.douya.model.TagData;
import com.kingsley.douya.net.NetWork;
import com.kingsley.douya.presenter.ipresenter.IMoviePresenter;
import com.kingsley.douya.ui.iview.IMovieView;

import java.util.ArrayList;
import java.util.Random;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MoviePresenter extends BasePresenter<IMovieView> implements IMoviePresenter {

    private static final String TAG = "MoviePresenter";

    private int start = 0;
    //关键字
    private String artist;

    private Observer<MovieModel> observer = new Observer<MovieModel>() {


        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mView.showError();
        }

        @Override
        public void onNext(MovieModel movieModel) {
            if (movieModel.getSubjects().size() > 0) {
                mView.showComplete((ArrayList<?>) movieModel.getSubjects());
                start = start + movieModel.getSubjects().size();
            } else {
                mView.showEmpty();
            }
        }
    };


    @Override
    public void loadingData(boolean isRefresh) {
        mView.showLoading();
        if (isRefresh) {
            artist = TagData.artists[new Random().nextInt(36)];
            start = 0;
        }
        subscription = NetWork.getDouBanApi()
                .searchTag(artist, start, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @Override
    protected void onDestroy() {
        unsubscribe();
    }
}
