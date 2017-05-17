package com.halohoop.androiddigin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.halohoop.androiddigin.frags.ListDataFragment;

public class MainActivity extends BaseAct {

    private ViewGroup mViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewGroup = (ViewGroup) findViewById(R.id.frag_container);
        ListDataFragment listDataFragment = ListDataFragment.newInstance(-1);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frag_container, listDataFragment,"")
                .commit();
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

}
