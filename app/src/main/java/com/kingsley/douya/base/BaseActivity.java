package com.kingsley.douya.base;

import android.os.Bundle;

import com.aitangba.swipeback.SwipeBackActivity;

import butterknife.ButterKnife;



public abstract class BaseActivity<V, T extends BasePresenter<V>> extends SwipeBackActivity {

    protected T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ButterKnife.bind(this);
        presenter = initPresenter();
        presenter.attach((V) this);
        initView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dettach();
        presenter.onDestroy();
    }

    abstract protected T initPresenter();

    abstract protected int setLayoutId();

    abstract protected void initView();

}
