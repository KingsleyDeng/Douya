package com.kingsley.douya.ui.iview;


import com.kingsley.douya.model.MovieSubjectsModel;

import java.util.List;


public interface IFavoriteView {
    /**
     * showLoading 方法主要用于页面请求数据时显示加载状态
     */
    void showLoading();

    /**
     * showEmpty 方法用于请求的数据为空的状态
     */
    void showEmpty();

    /**
     * loadingComplete 方法用于请求数据完成
     */
    void showComplete(List<MovieSubjectsModel> movieSubjectsModels);
}
