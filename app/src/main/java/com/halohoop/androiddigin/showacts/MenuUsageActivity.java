package com.halohoop.androiddigin.showacts;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.halohoop.androiddigin.R;
import com.halohoop.androiddigin.utils.Utils;

public class MenuUsageActivity extends AppCompatActivity {

    private CoordinatorLayout coordinar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usage);
        coordinar = (CoordinatorLayout) findViewById(R.id.coordinar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.log("onClick 's view is:"+view.getClass().getSimpleName());
                //Snackbar出现的同时fab按钮会向上调整位置，以腾出空间
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Snackbar.make(coordinar, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //Snackbar出现的同时fab按钮不会向上调整位置，不能腾出空间
//                Snackbar.make(coordinar的父亲view，如果有的话, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    //R.menu.menu1
    public void onSettingsClick(MenuItem item) {
        Utils.showToast(this,"吐司1");
    }

    //R.menu.menu1
    public void onTestPrefClick(MenuItem item) {
        Utils.showToast(this,"吐司2");
    }

}
