package com.halohoop.tableview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pooholah on 2017/6/21.
 */

public class TestView extends View {
    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int wSize = MeasureSpec.getSize(widthMeasureSpec);
//        int hSize = MeasureSpec.getSize(heightMeasureSpec);
//        int wMode = MeasureSpec.getMode(widthMeasureSpec);
//        int hMode = MeasureSpec.getMode(heightMeasureSpec);
//        int newWSpec = MeasureSpec.makeMeasureSpec(wSize/*大小自己改*/, MeasureSpec.EXACTLY);
//        int newHSpec = MeasureSpec.makeMeasureSpec(hSize/*大小自己改*/, MeasureSpec.EXACTLY);
//        super.onMeasure(newWSpec, newHSpec);
//
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int w = MeasureSpec.getSize(widthMeasureSpec);
//        Log.i(TAG, "onMeasure1: "+measuredWidth);
//        Log.i(TAG, "onMeasure1: "+measuredHeight);
//        Log.i(TAG, "onMeasure1: "+w);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        ViewGroup.LayoutParams layoutParams = getLayoutParams();

        int width = 0;
        int height = 0;
        if (wMode == MeasureSpec.AT_MOST) {
            width = 150;
            Log.i(TAG, "onMeasure: W-AtMost");
        } else {
            width = layoutParams.width;
            Log.i(TAG, "onMeasure: W-Exa");
        }

        if (hMode == MeasureSpec.AT_MOST) {
            height = 150;
            Log.i(TAG, "onMeasure: H-AtMost");
        } else {
            height = layoutParams.height;
            Log.i(TAG, "onMeasure: H-Exa");
        }
//        Log.i(TAG, "onMeasure: -------"+isLayoutRequested());
        Log.i(TAG, "onMeasure: --------------");

        setMeasuredDimension(width, height);
    }

    private static final String TAG = "TestView";
}
