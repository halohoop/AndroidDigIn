package com.halohoop.androiddigin.widgets;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;

import com.halohoop.androiddigin.utils.Utils;

/**
 * Created by Pooholah on 2017/5/5.
 */

public class RevealDrawable extends Drawable {
    private Drawable mSelectedDrawable;
    private Drawable mUnSelectedDrawable;
    private Rect mSelectedRect;
    private Rect mUnSelectedRect;

    public RevealDrawable(Drawable mSelectedDrawable, Drawable mUnSelectedDrawable) {
        this.mSelectedDrawable = mSelectedDrawable;
        this.mUnSelectedDrawable = mUnSelectedDrawable;
        mSelectedRect = new Rect();
        mUnSelectedRect = new Rect();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {//这个是控件的canvas
        int level = getLevel();
        Utils.log("draw: level=" + level);
        Rect bounds = getBounds();//现在的区域，也就是drawable被放入的控件的区域
        if (level == 0 || level == 10000) {//未进入或者已出去特定区域
            mUnSelectedDrawable.draw(canvas);
        } else if (level == 5000) {//完全在特定区域
            mSelectedDrawable.draw(canvas);
        } else {//0-5000，或者5000-10000，可以通过算法将这两种情况写到一起，减少代码量
            int w = bounds.width();
            int h = bounds.height();

            if (level < 5000) {
                float movePercent = level / 5000f;
                float leftPercent = 1 - movePercent;
                int selectedWidth = (int) (w * movePercent);
                int unSelectedWidth = (int) (w * leftPercent);
                Gravity.apply(Gravity.RIGHT,//从哪边开始扣，从左往右
                        selectedWidth, h,//需要扣的大小
                        bounds,//被扣的区域
                        mSelectedRect);//抠出来的区域
                Gravity.apply(Gravity.LEFT,//从哪边开始扣，从右往左
                        unSelectedWidth, h,//需要扣的大小
                        bounds,//被扣的区域
                        mUnSelectedRect);//抠出来的区域
            }else if(level > 5000){
                float movePercent = Math.abs(1 - level / 5000f);
                float leftPercent = 1 - movePercent;
                int unSelectedWidth = (int) (w * movePercent);
                int selectedWidth = (int) (w * leftPercent);
                Gravity.apply(Gravity.RIGHT,//从哪边开始扣，从左往右
                        unSelectedWidth, h,//需要扣的大小
                        bounds,//被扣的区域
                        mUnSelectedRect);//抠出来的区域
                Gravity.apply(Gravity.LEFT,//从哪边开始扣，从右往左
                        selectedWidth, h,//需要扣的大小
                        bounds,//被扣的区域
                        mSelectedRect);//抠出来的区域
            }

            canvas.save();
            canvas.clipRect(mSelectedRect);//规定显示区域
            //绘制左边
            mSelectedDrawable.draw(canvas);
            canvas.restore();
            canvas.save();
            canvas.clipRect(mUnSelectedRect);//规定显示区域
            //绘制右边
            mUnSelectedDrawable.draw(canvas);
            canvas.restore();
        }
    }

    @Override
    protected boolean onLevelChange(int level) {//外部调用setLevel
        invalidateSelf();//重绘，draw被调用
        return true;//需要持续更新
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        //传入的drawable需要手动设置一下边框
        this.mSelectedDrawable.setBounds(bounds);
        this.mUnSelectedDrawable.setBounds(bounds);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
