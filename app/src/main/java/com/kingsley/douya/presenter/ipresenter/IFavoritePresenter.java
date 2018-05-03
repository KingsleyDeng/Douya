package com.kingsley.douya.presenter.ipresenter;

import java.util.ArrayList;


public interface IFavoritePresenter {

    //加载内容
    void loadData();

    //删除内容
    boolean deleteData(ArrayList<String> selectIds);

}
