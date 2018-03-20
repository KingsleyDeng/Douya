package com.kingsley.douya;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kingsley.douya.adapter.FragmentAdapter;
import com.kingsley.douya.ui.fragments.AnimFragment;
import com.kingsley.douya.ui.fragments.MovieFragment;
import com.kingsley.douya.ui.fragments.TVFragment;
import com.kingsley.douya.ui.fragments.Top250Fragment;
import com.kingsley.douya.views.NoScrollViewPager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnTabSelectListener, OnTabReselectListener, View.OnClickListener, Toolbar.OnMenuItemClickListener {


    private NavigationView navigationview;

    private DrawerLayout drawerlayout;

    private Toolbar toolbar;

    private BottomBar bottomBar;

    private NoScrollViewPager viewpager;

    //当前tab页面
    private int tabAtPosition = 0;

    //Fragment的适配器
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        navigationview = findViewById(R.id.navigationview);
        drawerlayout = findViewById(R.id.drawerlayout);
        bottomBar = findViewById(R.id.bottomBar);
        viewpager = findViewById(R.id.viewpager);

        //toolbar设置初始标题
        toolbar.setTitle(getResources().getString(R.string.movie));
        //以上属性必须在setSupportActionBar(toolbar)之前调用
        setSupportActionBar(toolbar);
        //设置导航icon
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        //监听drawerLayout , 改变导航图标
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.app_name, R.string.app_name);
        toggle.syncState();
        drawerlayout.setDrawerListener(toggle);

        toolbar.setNavigationOnClickListener(this);//添加导航icon点击事件
        toolbar.setOnMenuItemClickListener(this);//添加子菜单点击事件

        //初始化Adapter
        fragments.add(new MovieFragment());
        fragments.add(new AnimFragment());
        fragments.add(new TVFragment());
        fragments.add(new Top250Fragment());
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        //viewpager配置
        viewpager.setNoScroll(true); // viewpager禁止滑动
        viewpager.setOffscreenPageLimit(4); // 默认加载5页
        viewpager.setAdapter(fragmentAdapter);


        bottomBar.setOnTabSelectListener(this);//底部导航栏时间
        bottomBar.setOnTabReselectListener(this);
    }

    /**
     * 该方法是用来加载toolbar菜单布局的
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载菜单文件
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onTabSelected(int tabId) {
        selectBottomId(tabId);
    }

    @Override
    public void onTabReSelected(int tabId) {
        selectBottomId(tabId);
    }

    private void selectBottomId(int tabId) {
        switch (tabId) {
            case R.id.tab_movie://电影
                setTitleAndColor(0, getResources().getString(R.string.movie), getResources().getColor(R.color.colorMovie), R.style.MovieThemeTransNav);
                break;
            case R.id.tab_anime://动漫
                setTitleAndColor(1, getResources().getString(R.string.anime), getResources().getColor(R.color.colorAnime), R.style.AnimeThemeTransNav);
                break;
            case R.id.tab_tv://电视剧
                setTitleAndColor(2, getResources().getString(R.string.tv), getResources().getColor(R.color.colorTV), R.style.TVThemeTransNav);
                break;
            case R.id.tab_top250://top250
                setTitleAndColor(3, getResources().getString(R.string.top250), getResources().getColor(R.color.colorTop250), R.style.Top250ThemeTransNav);
                break;
        }
    }

    /**
     * toolbar导航logo点击事件
     */
    @Override
    public void onClick(View view) {
        drawerlayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    /**
     * 设置title和主题颜色
     */
    private void setTitleAndColor(int item, String title, int color, int styleid) {
        tabAtPosition = item;

        viewpager.setCurrentItem(item,false);
        toolbar.setTitle(title);
        toolbar.setBackgroundColor(color);
        navigationview.setBackgroundColor(color);

    }

}
