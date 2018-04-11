package com.kingsley.douya.presenter;

import android.util.Log;

import com.kingsley.douya.base.BasePresenter;
import com.kingsley.douya.presenter.ipresenter.ITop250Presenter;
import com.kingsley.douya.ui.iview.ITop250View;

import java.util.ArrayList;




public class Top250Presenter extends BasePresenter<ITop250View> implements ITop250Presenter {

    private static final String TAG = "Top250Presenter";

    private int start = 0;



    @Override
    public void loadingData() {
        mView.showLoading();

    }

    @Override
    protected void onDestroy() {

    }
}
