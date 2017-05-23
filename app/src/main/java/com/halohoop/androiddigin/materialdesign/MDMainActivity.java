package com.halohoop.androiddigin.materialdesign;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.halohoop.androiddigin.R;
import com.halohoop.androiddigin.frags.CardViewShowFragment;
import com.halohoop.androiddigin.frags.OneClickFragment;
import com.halohoop.androiddigin.frags.RecyclerDataFragment;
import com.halohoop.androiddigin.materialdesign.datas.Cheeses;
import com.halohoop.androiddigin.utils.Utils;

import java.util.Random;

/**
 * Created by Pooholah on 2017/5/21.
 */

public class MDMainActivity extends AppCompatActivity
        implements OneClickFragment.ClickListener, View.OnClickListener,
        RecyclerDataFragment.OnRecyclerFragmentInteractionListener {

    //    private ViewPager mViewpager;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mRootDrawer;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private CoordinatorLayout mCoordinar;
    private Random RANDOM = new Random();
    private NavigationView mNaviView;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(versionCheck())finish();
//        setContentView(R.layout.activity_md_main);
        setContentView(R.layout.activity_md_main_with_parallax);
        mRootDrawer = (DrawerLayout) findViewById(R.id.root_drawer);
        mCoordinar = (CoordinatorLayout) findViewById(R.id.coordinar);
//        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        //设置数据才能执行下面的mTabLayout.setupWithViewPager
//        mViewpager.setAdapter(new FragAdapter(getSupportFragmentManager()));
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("MD Demo from H");
        setSupportActionBar(mToolbar);

        mNaviView = (NavigationView) findViewById(R.id.navView);
        NaviItemListener naviItemListener = new NaviItemListener();
        mNaviView.setNavigationItemSelectedListener(naviItemListener);
        mNaviView.getHeaderView(0).setOnClickListener(naviItemListener);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        //当没有使用DrawableLayout的时候可以使用这种方式为按钮修改图标
//        final ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_build);
//        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mRootDrawer, mToolbar,
                R.string.toggle_open, R.string.toggle_close);
//        mRootDrawer.setDrawerListener(mDrawerToggle);
        mRootDrawer.addDrawerListener(mDrawerToggle);
        //设置第二个
        mRootDrawer.addDrawerListener(new DrawerLayoutListener());
        //因为被拖出来的drawer遮住了，所以禁用动画
        mDrawerToggle.setDrawerSlideAnimationEnabled(false);
        //调用这句才让图标显示出来
        mDrawerToggle.syncState();

        showFragmentByReplace(createFragment(R.id.menu_item_drawer),false);
    }

    private boolean versionCheck() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            Utils.showToast(this,"Your phone must be LOLLIPOP or above!");
            return true;
        }
        return false;
    }

    @Override
    public void onClickInOneClick(View v, int which) {
        switch (which) {
            case R.id.menu_item_drawer:
                mRootDrawer.openDrawer(GravityCompat.START, true);
                break;
            case R.id.menu_item_toolbar:
                animateToolBar(true);
//                animateToolBar(false);
                break;
        }
    }

    private void animateToolBar(boolean useCompat) {
        if (useCompat) {
            ViewCompat.animate(mToolbar).setDuration(500)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            mToolbar.setRotation(0);
                            mToolbar.setRotationX(0);
                        }
                    }).rotation(10f).rotationX(720f).start();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mToolbar.animate().rotation(10f).rotationX(720f)
                        .withStartAction(new Runnable() {
                            @Override
                            public void run() {
                            }
                        })
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                mToolbar.setRotation(0);
                                mToolbar.setRotationX(0);
                            }
                        })
                        .setDuration(500).start();
            } else {
                mToolbar.animate().rotation(10f).rotationX(720f)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mToolbar.setRotation(0);
                                mToolbar.setRotationX(0);
                            }
                        })
                        .setDuration(500).start();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                //传入mCoordinar或者snackbar自己都可以使snackbar出现的时候调整Fab按钮
                Snackbar.make(mCoordinar, "切换日夜模式", Snackbar.LENGTH_SHORT).show();
//                Snackbar.make(v, "切换日夜模式", Snackbar.LENGTH_SHORT).show();
//                传入mRootDrawer不行，fab不会自动调整位置
//                Snackbar.make(mRootDrawer, "切换日夜模式", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    /*class FragAdapter extends FragmentStatePagerAdapter {
        public FragAdapter(FragmentManager fm) {
            super(fm);
        }

        //配合TabLayout才使用
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return Contents.TABS[position];
//        }

        @Override

        public Fragment getItem(int position) {
            return createFragment(position);
        }

        @Override
        public int getCount() {
            return Contents.TABS.length;
        }
    }*/

    //
    class DrawerLayoutListener implements DrawerLayout.DrawerListener {

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            Utils.log("slideOffset:" + slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            Utils.log("onDrawerOpened called");
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            Utils.log("onDrawerClosed called");
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            Utils.log("onDrawerStateChanged newState:" + newState);
        }
    }

    //侧滑抽屉点击事件
    class NaviItemListener implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            showFragmentByReplace(createFragment(item.getItemId()),false);
            //关闭drawer
            mRootDrawer.closeDrawer(GravityCompat.START);
            return true;
        }

        @Override
        public void onClick(View v) {
            //头部被点击
            showToast("Head been hit");
        }
    }

    private void showFragmentByReplace(Fragment fragment, boolean addToBackStack){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null) {
            fragmentTransaction.remove(mCurrentFragment);
            mCurrentFragment = null;
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(R.id.content_container,fragment).commit();
        mCurrentFragment = fragment;
    }

    private void showFragmentByAdd(Fragment fragment, boolean addToBackStack){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null) {
            fragmentTransaction.remove(mCurrentFragment);
            mCurrentFragment = null;
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.add(R.id.content_container,fragment).commit();
        mCurrentFragment = fragment;
    }

    private Fragment createFragment(int itemId) {
        Fragment frag = null;
        switch (itemId) {
            case R.id.menu_item_drawer:
            case R.id.menu_item_toolbar:
                frag = OneClickFragment.newInstance(itemId,
                        Utils.randomAColor(RANDOM));
                break;
            case R.id.menu_item_cardview:
                frag = CardViewShowFragment.newInstance(R.layout.layout_cardview);
                break;
            case R.id.menu_item_nestedscroll:
                frag = RecyclerDataFragment.newInstance();
                break;
        }
        return frag;
    }

    @Override
    public void onListFragmentInteraction(int clickIndex) {
        Utils.showToast(this, Cheeses.NAMES[clickIndex]);
    }

    @Override
    public void onBackPressed() {
        if (mRootDrawer.isDrawerOpen(GravityCompat.START)) {
            mRootDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void showToast(String s) {
        Utils.showToast(MDMainActivity.this, s);
    }
}
