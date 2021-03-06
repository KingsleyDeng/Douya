package com.kingsley.douya.ui.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kingsley.douya.MyApplication;
import com.kingsley.douya.R;
import com.kingsley.douya.adapter.MovieDetailAdapter;
import com.kingsley.douya.base.BaseActivity;
import com.kingsley.douya.model.MovieDetailModel;
import com.kingsley.douya.model.MovieSubjectsModel;
import com.kingsley.douya.presenter.MovieDetailPresenter;
import com.kingsley.douya.ui.iview.IMovieDetailView;

import java.util.ArrayList;

import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MovieDetailActivity extends BaseActivity<IMovieDetailView, MovieDetailPresenter> implements IMovieDetailView, Toolbar.OnMenuItemClickListener, View.OnClickListener, MovieDetailAdapter.IOnItemClickListener {

    @BindView(R.id.iv_movie_detail)
    ImageView ivMovieDetail;
    @BindView(R.id.toolbar_movie_detail)
    Toolbar toolbarMovieDetail;
    @BindView(R.id.collapsing_movie_detail)
    CollapsingToolbarLayout collapsingMovieDetail;
    @BindView(R.id.rv_movie_detail_director)
    RecyclerView rvMovieDetailDirector;
    @BindView(R.id.rv_movie_detail_cast)
    RecyclerView rvMovieDetailCast;
    @BindView(R.id.tv_movie_detail_year)
    TextView tvMovieDetailYear;
    @BindView(R.id.tv_movie_detail_country)
    TextView tvMovieDetailCountry;
    @BindView(R.id.tv_movie_detail_type)
    TextView tvMovieDetailType;
    @BindView(R.id.tv_movie_detail_average)
    TextView tvMovieDetailAverage;
    @BindView(R.id.rv_movie_detail_summary)
    TextView tvMovieDetailSummary;
    @BindView(R.id.srl_movie_detail)
    SwipeRefreshLayout srlMovieDetail;
    @BindView(R.id.ll_movie_detail)
    LinearLayout llMovieDetail;


    //用于判断是否收藏过
    private boolean isFavorite = false;
    //电影条目
    private MovieSubjectsModel movieSubjectsModel;

    @Override
    protected MovieDetailPresenter initPresenter() {
        return new MovieDetailPresenter();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void initView() {
        //加载图片
        Glide.with(this).load(getIntent().getStringExtra("img_url")).into(ivMovieDetail);
        //设置标题
        collapsingMovieDetail.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbarMovieDetail);
        toolbarMovieDetail.setNavigationIcon(R.drawable.ic_back_white_24dp);

        //配置SwipeRefreshLayout
        srlMovieDetail.setEnabled(false);
        srlMovieDetail.setColorSchemeColors(getIntent().getIntExtra("color", getResources().getColor(R.color.colorMovie)));

        checkIsNightMode();

        toolbarMovieDetail.setOnMenuItemClickListener(this);//子菜单点击事件 , 即点赞
        toolbarMovieDetail.setNavigationOnClickListener(this);//导航点击事件 , 即返回

        //配置RecyclerView
        initRecyclerView(rvMovieDetailDirector);
        initRecyclerView(rvMovieDetailCast);
        //获取条目信息
        movieSubjectsModel = (MovieSubjectsModel) getIntent().getSerializableExtra("movieSubject");
        //加载数据
        presenter.loadingData(getIntent().getStringExtra("id"));

    }

    private void checkIsNightMode() {
        if (MyApplication.NIGHT_MODE) {
            srlMovieDetail.setColorSchemeColors(getResources().getColor(R.color.colorNight));
            tvMovieDetailYear.setTextColor(getResources().getColor(R.color.color999));
            tvMovieDetailCountry.setTextColor(getResources().getColor(R.color.color999));
            tvMovieDetailType.setTextColor(getResources().getColor(R.color.color999));
            tvMovieDetailSummary.setTextColor(getResources().getColor(R.color.color999));
        }
    }

    /**
     * 配置RecyclerView
     */
    private void initRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);//设置布局管理器
        recyclerView.setNestedScrollingEnabled(false);
    }

    /**
     * 该方法用来加载Toolbar菜单布局
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载菜单文件
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    /**
     * 加载toolbar菜单布局后的方法
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //判断是否已收藏
        if (presenter.isFavorite(getIntent().getStringExtra("id"))) {
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_white_24dp);
            isFavorite = true;
        } else {
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_border_white_24dp);
            isFavorite = false;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * toolbar 菜单点击事件
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (srlMovieDetail.isRefreshing()) {
            //正在加载时不许操作
            Toast.makeText(this, getResources().getString(R.string.favorite_tip3), Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.action_favorite) {
            //判断是否已收藏
            if (!isFavorite) {
                //未收藏则插入数据
                if (presenter.saveFavorite(movieSubjectsModel)) {
                    item.setIcon(R.drawable.ic_favorite_white_24dp);
                    isFavorite = true;
                } else {
                    Toast.makeText(this, getResources().getString(R.string.favorite_tip1), Toast.LENGTH_SHORT).show();
                }
            } else {
                //已收藏则删除数据

                if (presenter.deleteFavorite(getIntent().getStringExtra("id"))) {
                    item.setIcon(R.drawable.ic_favorite_border_white_24dp);
                    isFavorite = false;
                } else {
                    Toast.makeText(this, getResources().getString(R.string.favorite_tip2), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (item.getItemId() == R.id.action_share) {
            //分享事件
            showShare();
        }
        return true;
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getIntent().getStringExtra("title"));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(getIntent().getStringExtra("img_url"));
        // text是分享文本，所有平台都需要这个字段
        oks.setText(getIntent().getStringExtra("title"));
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(getIntent().getStringExtra("img_url"));
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(getIntent().getStringExtra("alt"));
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(getIntent().getStringExtra("img_url"));
        // 启动分享GUI
        oks.show(this);
    }


    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void showLoading() {
        srlMovieDetail.setRefreshing(true);
    }

    @Override
    public void showEmpty() {
        srlMovieDetail.setRefreshing(false);
    }

    @Override
    public void showError(boolean is404) {
        srlMovieDetail.setRefreshing(false);
        if (is404) {
            Toast.makeText(this, getText(R.string.error_tips3), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getText(R.string.error_tips), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showComplete(MovieDetailModel movieDetailModel) {
        llMovieDetail.setVisibility(View.VISIBLE);
        srlMovieDetail.setRefreshing(false);
        //年份
        tvMovieDetailYear.setText(String.format(getResources().getString(R.string.detail_year), movieDetailModel.getYear()));
        //国家地区
        String country = "";
        for (int i = 0; i < movieDetailModel.getCountries().size(); i++) {
            if (i == movieDetailModel.getCountries().size() - 1) {
                country = country + movieDetailModel.getCountries().get(i);
            } else {
                country = country + movieDetailModel.getCountries().get(i) + "、";
            }
        }
        tvMovieDetailCountry.setText(String.format(getResources().getString(R.string.detail_country), country));
        //类型
        String type = "";
        for (int i = 0; i < movieDetailModel.getGenres().size(); i++) {
            if (i == movieDetailModel.getGenres().size() - 1) {
                type = type + movieDetailModel.getGenres().get(i);
            } else {
                type = type + movieDetailModel.getGenres().get(i) + "、";
            }
        }
        tvMovieDetailType.setText(String.format(getResources().getString(R.string.detail_type), type));
        //分数
        tvMovieDetailAverage.setText(String.format(getResources().getString(R.string.average), "" + movieDetailModel.getRating().getAverage()));
        //导演
        ArrayList<String> imgs = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        for (MovieDetailModel.DirectorsBean directorsBean : movieDetailModel.getDirectors()) {
            if (directorsBean.getAvatars() != null && directorsBean.getId() != null) {
                imgs.add(directorsBean.getAvatars().getMedium());
                names.add(directorsBean.getName());
                ids.add(directorsBean.getId());
            }
        }
        MovieDetailAdapter directorsAdapter = new MovieDetailAdapter(imgs, names, ids);
        rvMovieDetailDirector.setAdapter(directorsAdapter);
        directorsAdapter.setOnItemClickListener(this);
        //演员
        imgs = new ArrayList<>();
        names = new ArrayList<>();
        ids = new ArrayList<>();
        for (MovieDetailModel.CastsBean castsBean : movieDetailModel.getCasts()) {
            if (castsBean.getAvatars() != null && castsBean.getId() != null) {
                imgs.add(castsBean.getAvatars().getMedium());
                names.add(castsBean.getName());
                ids.add(castsBean.getId());
            }
        }
        MovieDetailAdapter castsAdapter = new MovieDetailAdapter(imgs, names, ids);
        rvMovieDetailCast.setAdapter(castsAdapter);
        castsAdapter.setOnItemClickListener(this);
        //简介
        tvMovieDetailSummary.setText(String.format(getResources().getString(R.string.detail_summary), movieDetailModel.getSummary()));
    }

    @Override
    public void onItemClick(String id, String name) {

    }

    @Override
    public void finish() {
        if (!isFavorite) {
            Intent intent = new Intent();
            intent.putExtra("isFavorite", isFavorite);
            setResult(MyApplication.RESULTCODE, intent);
        }
        super.finish();
    }

}
