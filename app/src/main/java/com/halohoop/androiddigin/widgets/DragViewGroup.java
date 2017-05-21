package com.halohoop.androiddigin.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Pooholah on 2017/5/18.
 */

public class DragViewGroup extends RelativeLayout {

    private View childAt0;
    private View childAt1;
    private ViewDragHelper viewDragHelper;

    public DragViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        viewDragHelper = ViewDragHelper.create(this, new CallBack());
    }

    private ViewGroup findContentLayout(ViewGroup parent) {
        if (parent == null) {
            return null;
        }
        if (parent.getId() != android.R.id.content) {
            return findContentLayout((ViewGroup) parent.getParent());
        } else {
            return parent;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        childAt0 = getChildAt(0);
        childAt1 = getChildAt(1);
    }

    class CallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == childAt0/* || childAt1 == child*/;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }
    }

}
