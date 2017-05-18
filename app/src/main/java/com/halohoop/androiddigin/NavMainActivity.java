package com.halohoop.androiddigin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.halohoop.androiddigin.categoris.Contents;
import com.halohoop.androiddigin.frags.ListDataFragment;
import com.halohoop.androiddigin.utils.Utils;

public class NavMainActivity extends BaseAct {

    private ViewPager mVp;
    private BottomNavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_main);
        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mVp = (ViewPager) findViewById(R.id.vp);
        mVp.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mVp.addOnPageChangeListener(new PageSelectedListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.getItem(0);
        item.setTitle("切换为全部模式");
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_effect:
                    mVp.setCurrentItem(0);
                    return true;
                case R.id.navigation_templete:
                    mVp.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };

    //R.menu.menu1
    public void onMenuClick(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        startAct(intent);
        finish();
    }

    private class PageSelectedListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            //此处以后需要维护，因为需要把BottomNavigationView换掉
            View childAt = ((ViewGroup) mNavigation.getChildAt(0)).getChildAt(position);
            mNavigation.setSelectedItemId(childAt.getId());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private static class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Utils.log("getItem");
            ListDataFragment listDataFragment = ListDataFragment.newInstance(position);
            return listDataFragment;
        }

        @Override
        public int getCount() {
            return Contents.CATEGORIS_COUNT;
        }
    }

}
