package com.kingsley.douya.base;

import android.content.Context;



public abstract class BasePresenter<V> {

    protected V mView;
    protected Context context;

    /**
     * BaseActivity调用这个方法
     */
    protected void attach(V mView) {
        this.context = (Context) mView;
        this.mView = mView;
    }

    /**
     * BaseFragment调用这个方法
     */
    protected void attach(Context context , V mView) {
        this.context = context;
        this.mView = mView;
    }

    protected void dettach() {
        mView = null;
    }

    /**
     * Presenter销毁时调用
     */
    abstract protected void onDestroy();



}
