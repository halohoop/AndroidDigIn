package com.halohoop.androiddigin.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.halohoop.androiddigin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pooholah on 2017/5/11.
 * 练习RadialGradient用，不能用作实际项目
 */

public class RadialGradientRippleView extends View {

    private Bitmap mOriBitmap;
    private Bitmap mScaledBitmap;
    private Bitmap mRippleScaledBitmap;
    //放大倍数
    private float RIPPLE_SCALE_FACTOR = 1.1f;
    private int mDisW;
    private int mDisH;
    private List<RippleStuff> mRippleStuffs = new ArrayList<>();
    private BitmapShader mBitmapShader;//此shader每个波纹都可以用同一个，因此为全局变量

    public RadialGradientRippleView(Context context) {
        this(context, null);
    }

    public RadialGradientRippleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadialGradientRippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mOriBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.pic)).getBitmap();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScaledBitmap = Bitmap.createScaledBitmap(mOriBitmap, w, h, true);
        mRippleScaledBitmap = Bitmap.createScaledBitmap(mOriBitmap,
                (int) (w * RIPPLE_SCALE_FACTOR), (int) (h * RIPPLE_SCALE_FACTOR), true);
        int width1 = mScaledBitmap.getWidth();
        int height1 = mScaledBitmap.getHeight();
        int width2 = mRippleScaledBitmap.getWidth();
        int height2 = mRippleScaledBitmap.getHeight();
        mDisW = Math.abs(width1 - width2) / 2;
        mDisH = Math.abs(height1 - height2) / 2;//算出两图中点相聚的距离，就是放大后需要矫正的位移距离
        mOriBitmap.recycle();
        mOriBitmap = null;
        //这里的模式无所谓，因为在屏幕外看不到
        mBitmapShader = new BitmapShader(mRippleScaledBitmap,
                Shader.TileMode.MIRROR,//这里的模式无所谓，因为在屏幕外看不到
                Shader.TileMode.MIRROR);
        Matrix matrix = new Matrix();
        matrix.setTranslate(-mDisW, -mDisH);
        mBitmapShader.setLocalMatrix(matrix);
    }

    private int mRadiusWidth = 200;

    public void setmRadiusWidth(int mRadiusWidth) {
        if (mRadiusWidth < 10) {
            this.mRadiusWidth = 10;
            Toast.makeText(getContext(), "不能在小了", Toast.LENGTH_SHORT).show();
        }else{
            this.mRadiusWidth = mRadiusWidth;
        }
    }
    public int getmRadiusWidth() {
        return this.mRadiusWidth;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            //向中间0.5靠拢
            RadialGradient radialGradient = new RadialGradient(x + mDisW, y + mDisH, mRadiusWidth,
                    new int[]{Color.TRANSPARENT, Color.WHITE, Color.TRANSPARENT},
                    new float[]{0.15f, 0.45f, 0.75f},//一半一半
                    Shader.TileMode.REPEAT);
            ComposeShader composeShader = new ComposeShader(radialGradient,
                    mBitmapShader,
                    PorterDuff.Mode.SRC_IN);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setShader(composeShader);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(mRadiusWidth);

            RippleStuff rippleStuff = new RippleStuff();
            rippleStuff.radius = mRadiusWidth;
            rippleStuff.paint = paint;
            rippleStuff.x = x;
            rippleStuff.y = y;
            mRippleStuffs.add(rippleStuff);
            createAnimatorAndStart(rippleStuff);
        }
        return true;
    }

    private void createAnimatorAndStart(final RippleStuff rippleStuff) {
        float farestDistance = getFarestDistance(rippleStuff.x, rippleStuff.y);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mRadiusWidth, farestDistance)
                .setDuration(2000);//可以换成自定义属性设置扩散速度
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                rippleStuff.radius = animatedValue;
                if (mRippleStuffs.indexOf(rippleStuff) == mRippleStuffs.size() - 1) {
                    invalidate();
                }
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mRippleStuffs.remove(rippleStuff);
            }
        });
        valueAnimator.start();
    }

    private float getFarestDistance(float x, float y) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        float toLeftTop = getTwoPointDistance(x, y, 0, 0);
        float toRightTop = getTwoPointDistance(x, y, measuredWidth, 0);
        float toLeftBottom = getTwoPointDistance(x, y, 0, measuredHeight);
        float toRightBottom = getTwoPointDistance(x, y, measuredWidth, measuredHeight);
        float max = getMax(toLeftTop, toRightTop, toLeftBottom, toRightBottom);
        return max;
    }

    private float getTwoPointDistance(float x, float y, int x1, int y1) {
        float absX = Math.abs(x - x1);
        float absY = Math.abs(y - y1);
        return (float) Math.sqrt(absX * absX + absY * absY);
    }

    private float getMax(float... values) {
        if (values.length == 0) {
            return -1;
        } else if (values.length == 1) {
            return values[0];
        }
        float max = -1;
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                max = values[0];
                continue;
            }
            max = Math.max(max, values[i]);
        }
        return max;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画原图
        canvas.drawBitmap(mScaledBitmap, 0, 0, null);
        //test
//        canvas.drawBitmap(mRippleScaledBitmap, -mDisW, -mDisH, null);//确认放大后的图片位置是对的
        if (mRippleStuffs.size() > 0) {
            for (int i = 0; i < mRippleStuffs.size(); i++) {
                RippleStuff rippleStuff = mRippleStuffs.get(i);
                canvas.drawCircle(rippleStuff.x, rippleStuff.y,
                        rippleStuff.radius, rippleStuff.paint);
            }
        }
    }

    //生存周期和此view一致，因此可以不用static
    class RippleStuff {
        Paint paint;
        float x;
        float y;
        float radius;
    }
}
