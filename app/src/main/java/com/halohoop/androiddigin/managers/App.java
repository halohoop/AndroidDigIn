package com.halohoop.androiddigin.managers;

import android.app.Application;

import com.halohoop.androiddigin.categoris.Contents;

/**
 * Created by Pooholah on 2017/5/17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (Contents.CONTENTS.length != Contents.CATEGORIS.length) {
            throw new RuntimeException("数量不对1");
        }
        if (Contents.CONTENTS.length != Contents.ITEM_TYPES.length) {
            throw new RuntimeException("数量不对2");
        }
    }
}
