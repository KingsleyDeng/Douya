package com.kingsley.douya.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingsley.douya.R;
import com.kingsley.douya.base.BaseLazyFragment;
import com.kingsley.douya.presenter.Top250Presenter;
import com.kingsley.douya.ui.iview.ITop250View;

import java.util.ArrayList;

/**
 * Created by Stephen on 2018/3/19.
 */

public class Top250Fragment extends BaseLazyFragment<ITop250View, Top250Presenter> implements ITop250View {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top250,container,false);
        return view;
    }

    @Override
    protected Top250Presenter initPresenter() {
        return null;
    }

    @Override
    protected int setLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showComplete(ArrayList<?> models) {

    }
}
