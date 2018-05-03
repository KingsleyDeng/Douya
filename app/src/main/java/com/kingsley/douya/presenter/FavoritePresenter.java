package com.kingsley.douya.presenter;

import com.kingsley.douya.base.BasePresenter;
import com.kingsley.douya.model.MovieSubjectsModel;
import com.kingsley.douya.presenter.ipresenter.IFavoritePresenter;
import com.kingsley.douya.ui.iview.IFavoriteView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class FavoritePresenter extends BasePresenter<IFavoriteView> implements IFavoritePresenter {

    private static final String TAG = "FavoritePresenter";

    @Override
    public void loadData() {
        mView.showLoading();

        Observable.create(new Observable.OnSubscribe<List<MovieSubjectsModel>>() {
            @Override
            public void call(Subscriber<? super List<MovieSubjectsModel>> subscriber) {
                //数据库取出数据
                subscriber.onNext(DataSupport.order("id desc").find(MovieSubjectsModel.class, true));
            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<List<MovieSubjectsModel>>() {
                    @Override
                    public void call(List<MovieSubjectsModel> movieSubjectsModels) {
                        //判断有无数据
                        if (movieSubjectsModels.size() > 0) {
                            mView.showComplete(movieSubjectsModels);
                        } else {
                            mView.showEmpty();
                        }
                    }
                });
    }

    @Override
    public boolean deleteData(ArrayList<String> selectIds) {
        for (String id : selectIds) {
            DataSupport.deleteAll(MovieSubjectsModel.class, "movie_id = ?", id);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        unsubscribe();
    }

}
