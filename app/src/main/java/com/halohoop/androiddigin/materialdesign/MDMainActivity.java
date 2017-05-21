package com.halohoop.androiddigin.materialdesign;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.halohoop.androiddigin.R;
import com.halohoop.androiddigin.frags.OneClickFragment;
import com.halohoop.androiddigin.frags.RecyclerDataFragment;
import com.halohoop.androiddigin.materialdesign.datas.Cheeses;
import com.halohoop.androiddigin.materialdesign.datas.Contents;
import com.halohoop.androiddigin.utils.Utils;

import java.util.Random;

/**
 * Created by Pooholah on 2017/5/21.
 */

public class MDMainActivity extends AppCompatActivity
        implements OneClickFragment.ClickListener, View.OnClickListener,
        RecyclerDataFragment.OnRecyclerFragmentInteractionListener {

    private ViewPager mViewpager;
    private TabLayout mTabLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mRootDrawer;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private CoordinatorLayout mCoordinar;
    private Random RANDOM = new Random();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_main);
        mRootDrawer = (DrawerLayout) findViewById(R.id.root_drawer);
        mCoordinar = (CoordinatorLayout) findViewById(R.id.coordinar);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        //设置数据才能执行下面的mTabLayout.setupWithViewPager
        mViewpager.setAdapter(new FragAdapter(getSupportFragmentManager()));
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewpager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("MD Demo from H");
        setSupportActionBar(mToolbar);

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
    }

    @Override
    public void onClickInOneClick(View v, int which) {
        switch (which) {
            case 0:
                mRootDrawer.openDrawer(GravityCompat.START, true);
                break;
            case 1:
                animateToolBar(true);
//                animateToolBar(false);
                break;
            case 2:
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

    class FragAdapter extends FragmentStatePagerAdapter {
        public FragAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Contents.TABS[position];
        }

        @Override

        public Fragment getItem(int position) {
            Fragment frag = null;
            switch (position) {
                case 0:
                case 1:
                    frag = OneClickFragment.newInstance(position,
                            Utils.randomAColor(RANDOM));
                    break;
                case 2:
                    frag = RecyclerDataFragment.newInstance();
                    break;
            }
            return frag;
        }

        @Override
        public int getCount() {
            return Contents.TABS.length;
        }
    }

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

    @Override
    public void onListFragmentInteraction(int clickIndex){
        Utils.showToast(this, Cheeses.NAMES[clickIndex]);
    }

    @Override
    public void onBackPressed() {
        if (mRootDrawer.isDrawerOpen(GravityCompat.START)) {
            mRootDrawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
