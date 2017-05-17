package com.halohoop.androiddigin;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.halohoop.androiddigin.frags.ListDataFragment;
import com.halohoop.androiddigin.utils.Utils;

public class MainActivity extends BaseAct {

    private ViewGroup mViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewGroup = (ViewGroup) findViewById(R.id.frag_container);
        if (!mIsBeenThroughOnSaveInstanceState) {
            mViewGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListDataFragment listDataFragment = ListDataFragment.newInstance(-1);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.frag_container, listDataFragment, "")
                            .commit();
                    mIsBeenThroughOnSaveInstanceState = true;
                    mViewGroup.setOnClickListener(null);
                }
            });
            Utils.showToast(this, "点击任意空白加载界面");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.getItem(0);
        item.setTitle("切换为分类模式");
        return true;
    }

    //R.menu.menu1
    public void onMenuClick(MenuItem item) {
        Intent intent = new Intent(this, NavMainActivity.class);
        startAct(intent);
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Utils.log("MainActivity onConfigurationChanged");
    }
}
