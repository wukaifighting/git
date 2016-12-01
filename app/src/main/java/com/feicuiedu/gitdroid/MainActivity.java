package com.feicuiedu.gitdroid;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.feicuiedu.gitdroid.commons.ActivityUtils;
import com.feicuiedu.gitdroid.github.HotRepoFragment;
import com.feicuiedu.gitdroid.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.navigationView)
    NavigationView mNavigationView;
    @BindView(R.id.drawerLayout)//抽屉效果
            DrawerLayout mDrawerLayout;
    private Button mBtnLogin;
    private ImageView mIvIcon;
    private ActivityUtils activityutils;
    HotRepoFragment mHotRepoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置当前视图(更改了当前视图内容,将导致onContentChanged方法触发)
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        activityutils = new ActivityUtils(MainActivity.this);
        ButterKnife.bind(this);

        /**
         * 需要处理的视图
         * 1. toolbar
         * 2. DrawerLayout
         * 3. NavigationView
         *
         */
        //设置ActionBar，主题里面没有TOOLBAR，可以使用设置ActionBar
        setSupportActionBar(mToolbar);
        // 设置监听
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //同步状态
        toggle.syncState();
        // 设置DrawerLayout的侧滑监听
        mDrawerLayout.addDrawerListener(toggle);
        mNavigationView.setNavigationItemSelectedListener(this);
        mBtnLogin = ButterKnife.findById(mNavigationView.getHeaderView(0), R.id.btnLogin);
        mIvIcon = ButterKnife.findById(mNavigationView.getHeaderView(0), R.id.ivIcon);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/12/1 跳转到登录页面
                activityutils.startActivity(LoginActivity.class);
                finish();
            }
        });
        //设置在主界面显示的热门
        mHotRepoFragment = new HotRepoFragment();
        replacefragment(mHotRepoFragment);
    }


    //写方法，使其在指定的方法中直接选择调换
    private void replacefragment(Fragment fragment) {
        //管理器
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    //主要做了我们基本登录信息的改变
    @Override
    protected void onStart() {
        // TODO: 2016/12/1 展示登录用户信息
        super.onStart();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.isChecked()) {
            item.setChecked(false);
        }
        switch (item.getItemId()) {
            // 最热门
            case R.id.github_hot_repo:
                // TODO: 2016/12/1 切换到最热门的视图
                if (mHotRepoFragment.isAdded()) {
                    replacefragment(mHotRepoFragment);
                }
                break;
            //开发者
            case R.id.github_hot_coder:
                // TODO: 2016/12/1 切换到开发者的视图
                break;
            //流行趋势
            case R.id.github_trend:
                // TODO: 2016/12/1  切换到流行趋势的视图
                break;
            //我的收藏
            case R.id.arsenal_my_repo:
                // TODO: 2016/12/1  切换到我的收藏的视图
                break;
            //推荐
            case R.id.arsenal_recommend:
                // TODO: 2016/12/1  切换到推荐的视图
                break;
            //每日干货
            case R.id.tips_daily:
                // TODO: 2016/12/1  切换到每日干货的视图
                break;
            //分享
            case R.id.tips_share:
                // TODO: 2016/12/1  切换到分享的视图
                break;

        }
        //选择木一项之后，切换相应的fragment,不安比抽屉
        mDrawerLayout.closeDrawer(GravityCompat.START);
        //返回true,.代表事件已经处理,代表该项已经被选择了
        return true;
    }

    @Override
    public void onBackPressed() {
        //删除这个，因为后面已经开始执行了super.onBackPressed();
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
