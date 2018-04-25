package com.kingsley.douya.presenter;


import android.util.Log;

import com.kingsley.douya.base.BasePresenter;
import com.kingsley.douya.model.MovieModel;
import com.kingsley.douya.net.NetWork;
import com.kingsley.douya.presenter.ipresenter.ITVPresenter;
import com.kingsley.douya.ui.iview.ITVView;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TVPresenter extends BasePresenter<ITVView> implements ITVPresenter {

    private static final String TAG = "TVPresenter";

    private int start = 0;

    private Observer<MovieModel> observer = new Observer<MovieModel>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError: ", e);
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
    public void loadingData() {
        mView.showLoading();
        subscription = NetWork.getDouBanApi()
                .searchTag("电视剧", start, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @Override
    protected void onDestroy() {
        unsubscribe();
    }
}
