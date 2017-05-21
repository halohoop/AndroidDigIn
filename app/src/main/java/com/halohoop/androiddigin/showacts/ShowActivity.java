package com.halohoop.androiddigin.showacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Pooholah on 2017/5/18.
 */

public class ShowActivity extends AppCompatActivity {
    public final static String RES_ID = "res_id";
    public final static String SHOW_ACTIONBAR = "show_actionbar";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int resId = intent.getIntExtra(RES_ID, -1);
        boolean showActionbar = intent.getBooleanExtra(SHOW_ACTIONBAR, false);
        if (!showActionbar) {
            //全屏
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
        }
        if (resId == -1) {
            throw new RuntimeException("请传入需要展示的layout 的 id");
        }
        setContentView(resId);
        additionalActionsAfterSCV();
    }

    protected void additionalActionsAfterSCV() {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
