package com.halohoop.androiddigin.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Pooholah on 2017/5/28.
 */

public class TouchDelegateViewGroup extends FrameLayout {
    private int color = Color.RED;
    private Rect rect = null;
    private Paint paint = null;

    public TouchDelegateViewGroup(@NonNull Context context) {
        super(context);
    }

    public TouchDelegateViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchDelegateViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDelegateAreaColor(@ColorInt int color, Rect rect) {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        this.rect = rect;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rect != null) {
            canvas.drawRect(rect, paint);
        }
    }
}
