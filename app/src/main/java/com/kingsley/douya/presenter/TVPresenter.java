package com.kingsley.douya.presenter;


import com.kingsley.douya.base.BasePresenter;
import com.kingsley.douya.presenter.ipresenter.ITVPresenter;
import com.kingsley.douya.ui.iview.ITVView;

public class TVPresenter extends BasePresenter<ITVView> implements ITVPresenter {

    private static final String TAG = "TVPresenter";

    private int start = 0;


    @Override
    public void loadingData() {
        mView.showLoading();

    }

    @Override
    protected void onDestroy() {
    }
}