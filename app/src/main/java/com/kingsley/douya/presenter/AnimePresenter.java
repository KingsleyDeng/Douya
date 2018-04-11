package com.kingsley.douya.presenter;


import com.kingsley.douya.base.BasePresenter;
import com.kingsley.douya.presenter.ipresenter.IAnimePresenter;
import com.kingsley.douya.ui.iview.IAnimeView;




public class AnimePresenter extends BasePresenter<IAnimeView> implements IAnimePresenter {

    private static final String TAG = "AnimePresenter";

    private int start = 0;



    @Override
    public void loadingData() {
        mView.showLoading();

    }

    @Override
    protected void onDestroy() {
    }
}
