package com.kingsley.douya.presenter.ipresenter;


import com.kingsley.douya.model.MovieSubjectsModel;

public interface IMovieDetailPresenter {

    //加载数据
    void loadingData(String id);
    //判断是否已收藏
    boolean isFavorite(String id);
    //插入数据
    boolean saveFavorite(MovieSubjectsModel movieSubjectsModel);
    //删除数据
    boolean deleteFavorite(String id);

}
