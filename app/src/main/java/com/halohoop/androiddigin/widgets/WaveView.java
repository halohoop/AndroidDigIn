package com.halohoop.androiddigin.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.halohoop.androiddigin.R;

/**
 * Created by Pooholah on 2017/6/3.
 */

public class WaveView extends View {
    private Path wavePath = new Path();
    private Paint paint = null;
    private int color = Color.RED;
    private int singleWaveWidth = 0;
    private int singleWaveWidth_fix = 0;
    /**
     * 动画偏移
     */
    private int deltaX = 0;
    private int deltaY = 0;
    private int deltaX_fix = 0;
    private int deltaY_fix = 0;
    /**
     * 一个波浪的高度
     */
    private int singleWaveHeightDeltaY = 100;
    private int singleWaveHeightDeltaY_fix = 100;
    /**
     * 水位的高度
     */
    private int startLeveldrawY = 0;
    private int startLeveldraw_fix = 0;
    private int viewHeight;

    public WaveView(Context context) {
        super(context);
        init(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        singleWaveWidth = context.getResources().getDimensionPixelSize(R.dimen.wave_width);
        singleWaveWidth_fix = singleWaveWidth;
        singleWaveHeightDeltaY = context.getResources().getDimensionPixelSize(R.dimen.wave_height);
        singleWaveHeightDeltaY_fix = singleWaveHeightDeltaY;
        color = context.getResources().getColor(R.color.wave_color);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        startLeveldraw_fix = startLeveldrawY;
    }

    private int getWaterline(int drawY) {
        return viewHeight - drawY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.scale(0.1f, 0.1f, getMeasuredWidth() >> 1, getMeasuredHeight() >> 1);
        wavePath.reset();
        wavePath.moveTo(- 2*singleWaveWidth + deltaX, getWaterline(startLeveldrawY + deltaY));
        for (int i = getMeasuredWidth() - singleWaveWidth;
             i < getMeasuredWidth() + singleWaveWidth; i += singleWaveWidth) {
            wavePath.rQuadTo(singleWaveWidth >> 1, -singleWaveHeightDeltaY,
                    singleWaveWidth, 0);
            wavePath.rQuadTo(singleWaveWidth >> 1, singleWaveHeightDeltaY,
                    singleWaveWidth, 0);
        }
        wavePath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        wavePath.lineTo(0, getMeasuredHeight());
        wavePath.close();
        canvas.drawPath(wavePath, paint);

        {
            deltaX+=30;
            if (deltaX > 2*singleWaveWidth) {//移动了两个周期才能重置，
                deltaX = deltaX_fix;
            }
            if (deltaY > getMeasuredHeight() && yReverse != true) {
                yReverse = true;
            } else if (deltaY < 0 && yReverse != false) {
                yReverse = false;
            }
            if (yReverse) deltaY-=3;
            else deltaY+=3;
        }
        invalidate();
//        postInvalidateDelayed(16);
    }

    boolean yReverse = false;
}
