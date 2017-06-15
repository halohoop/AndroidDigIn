package com.halohoop.androiddigin.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by Pooholah on 2017/6/15.
 */

public class VelocityTrackerDemoView extends View {


    public VelocityTrackerDemoView(Context context) {
        super(context);
    }

    public VelocityTrackerDemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VelocityTrackerDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private VelocityTracker mVelocityTracker;
    private int mMaxVelocity;
    private int mPointerId;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mVelocityTracker = VelocityTracker.obtain();
                mMaxVelocity = ViewConfiguration.get(getContext()).getMaximumFlingVelocity();
                mPointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                //求伪瞬时速度
                mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                final float velocityX = mVelocityTracker.getXVelocity(mPointerId);
                final float velocityY = mVelocityTracker.getYVelocity(mPointerId);
                updateData(velocityX, velocityY);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                releaseVelocityTracker();
                break;
        }
        return true;
    }

    float velocityX;
    float velocityY;

    private void updateData(float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaint.setTextSize(20);
        if (mRect == null) {
            mRect = new Rect();
        }
        mPaint.getTextBounds("VelocityX:", 0, "VelocityX:".length() - 1, mRect);
    }

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect mRect = null;

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawText("滑动空白区域得到x/y方向速度值:", 0, mRect.bottom + 30, mPaint);
        canvas.drawText("VelocityX:" + velocityX, 0, 200+mRect.bottom, mPaint);
        canvas.drawText("VelocityY:" + velocityY, 0, 250+mRect.bottom + mRect.bottom, mPaint);
    }

    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }
}
