package com.halohoop.tableview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by Pooholah on 2017/6/22.
 */

public class MyRela extends RelativeLayout {
    public MyRela(Context context) {
        super(context);
    }

    public MyRela(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRela(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int wm = MeasureSpec.getMode(widthMeasureSpec);
        int hm = MeasureSpec.getMode(heightMeasureSpec);
        Log.i(TAG, "onMeasure: MyRelativeLayout:w:" + wm + "--h:" + hm);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private static final String TAG = "MyRelativeLayout";
}
