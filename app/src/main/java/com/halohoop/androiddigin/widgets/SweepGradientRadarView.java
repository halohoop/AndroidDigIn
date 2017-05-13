package com.halohoop.androiddigin.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Pooholah on 2017/5/12.
 */

public class SweepGradientRadarView extends View {

    //雷达半径大小
    private float mRadarRadius = 500;
    private float mSpotRadius = 80 / 2;
    //分为多少
    private int mHowManyDividers = 6;
    private float mDividerWidth = mRadarRadius / mHowManyDividers;
    private SweepGradient mSweepGradient;
    //雷达画笔
    private Paint mPaint;
    //雷达刻度画笔
    private Paint mDividerPaint;
    private int mDegrees = 0;
    //出现名字的点
    private List<Spot> mSpots = new ArrayList<>();
    private int mCx;
    private int mCy;
    private int mCircleDividerColor;
    private int mCrossDividerColor;
    private int mRadarBgColor;

    public SweepGradientRadarView(Context context) {
        super(context);
    }

    public SweepGradientRadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SweepGradientRadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCx = w / 2;
        mCy = h / 2;
        mSweepGradient = new SweepGradient(mCx, mCy,
                new int[]{Color.argb(25, 0, 255, 0),
                        Color.argb(80, 0, 255, 0),
                        Color.argb(200, 0, 255, 0)},
                new float[]{0, 0.95f, 1.0f});
        mPaint = new Paint();
        mPaint.setShader(mSweepGradient);
        mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleDividerColor = Color.BLACK;
        mCrossDividerColor = Color.WHITE;
        mRadarBgColor = Color.rgb(154, 154, 154);
        mDividerPaint.setColor(mCircleDividerColor);
        mDividerPaint.setStrokeWidth(3);
        mDividerPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //背景 mRadarBgColor
        canvas.drawColor(mRadarBgColor);
        {
            //画刻度
            mDividerPaint.setColor(mCrossDividerColor);
            canvas.drawLine(mCx - mRadarRadius, mCy,
                    mCx + mRadarRadius, mCy, mDividerPaint);
            canvas.drawLine(mCx, mCy - mRadarRadius,
                    mCx, mCy + mRadarRadius, mDividerPaint);
            //短刻度线条
            canvas.save();
            for (int i = 0; i < 8; i++) {
                if (i > 3) {
                    canvas.drawLine(mCx - 50, mCy - 4.5f * mDividerWidth,
                            mCx + 50, mCy - 4.5f * mDividerWidth, mDividerPaint);
                } else {
                    canvas.drawLine(mCx - 50, mCy - 2.5f * mDividerWidth,
                            mCx + 50, mCy - 2.5f * mDividerWidth, mDividerPaint);
                }
                canvas.rotate(90, mCx, mCy);
            }
            canvas.restore();
            mDividerPaint.setColor(mCircleDividerColor);
            for (int i = 0; i < mHowManyDividers; i++) {
                if (i == 0) {
                    continue;
                }
                canvas.drawCircle(mCx, mCy, mDividerWidth * (i + 1), mDividerPaint);
            }
        }
        {
            //画扫出来的东西
            if (mSpots.size() > 0) {
                for (int i = 0; i < mSpots.size(); i++) {
                    Spot spot = mSpots.get(i);
                    if (spot.isAnimating) {
                        canvas.drawRect(spot.animRectF, spot.paint);
                    } else {
                        canvas.drawBitmap(spot.bitmap,
                                spot.originRectF.left,
                                spot.originRectF.top, null);
                    }
                }
            }
        }
        {
            canvas.save();
            ++mDegrees;
            if (mDegrees == 360) {
                mDegrees = 0;
            }
            canvas.rotate(mDegrees, mCx, mCy);
            canvas.drawCircle(mCx, mCy, mRadarRadius, mPaint);
            canvas.restore();
        }
        postInvalidate();
    }

    public void newSpot(String data) {
        Spot spot = new Spot();
        spot.bitmap = createSpotBitmap(data);
        spot.originRectF = createSpotRect(mRadarRadius, mSpotRadius);
        spot.animRectF = new RectF(spot.originRectF);
        spot.paint = new Paint();
        spot.bitmapShader = new BitmapShader(spot.bitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        spot.matrix = new Matrix();
        spot.bitmapShader.setLocalMatrix(spot.matrix);
        spot.paint.setShader(spot.bitmapShader);
        createSpotShowAnimatorAndStart(spot);
        mSpots.add(spot);
    }

    private void createSpotShowAnimatorAndStart(final Spot spot) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.5f, 0.5f, 1).setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                //上下拉长，左右缩短，基于底部中点
                float xFacotr = value;
                float yFacotr = 2 - xFacotr;
                float halfOriRectFWidth = spot.originRectF.width() / 2.0f;
                float oriHeight = spot.originRectF.height();
                {
                    spot.matrix.reset();
                    spot.matrix.setTranslate(spot.animRectF.left, spot.animRectF.top);
                    spot.matrix.postScale(xFacotr, yFacotr,//上下拉长，左右缩短
                            spot.animRectF.centerX(),//基于控件中点
                            spot.animRectF.bottom);
                    spot.bitmapShader.setLocalMatrix(spot.matrix);
                    spot.paint.setShader(spot.bitmapShader);
                }
                {
                    //相应的矩形也要有所变化，中点的x是不变的，bottom是不变的，因为上面的基于底部中点
                    float changedHalfOriWidth = halfOriRectFWidth * xFacotr;
                    float changedOriHeight = oriHeight * yFacotr;
                    spot.animRectF.left = spot.animRectF.centerX() - changedHalfOriWidth;
                    spot.animRectF.right = spot.animRectF.centerX() + changedHalfOriWidth;
                    spot.animRectF.top = spot.animRectF.bottom - changedOriHeight;
                }
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                spot.isAnimating = false;
                spot.recycle();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                spot.isAnimating = true;
            }
        });
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.start();
    }

    private RectF createSpotRect(float restrictRadius, float radius) {
        //圆形的外切矩形，限制的区域
        RectF restrictRect = new RectF(0, 0, restrictRadius * 2, restrictRadius * 2);
        Random random = new Random();
        //先确定左上角
        float left = 0;
        float top = 0;
        while (true) {
            //随机范围内的左上角坐标
            left = random.nextInt((int) (restrictRect.right - radius));
            top = random.nextInt((int) (restrictRect.bottom - radius));
            //得到圆心
            float tmpCx = left + radius;
            float tmpCy = top + radius;
            float disX = Math.abs(restrictRect.centerX() - tmpCx);
            float disY = Math.abs(restrictRect.centerY() - tmpCy);
            //圆心距离
            float centerDistance = (float) Math.sqrt(disX * disX + disY * disY);
            if (centerDistance > mRadarRadius - radius) {
                continue;
            }
            float distanceLeft = Math.abs(restrictRadius - centerDistance);
            if (distanceLeft > radius) {//如果符合条件（不超出范围）跳出循环
                break;
            }
        }
        float right = left + radius * 2;
        float bottom = top + radius * 2;

        //最终的位置
        //最终偏移的距离
        float offsetX = Math.abs(restrictRect.centerX() - mCx);//TODO 没有适配的bug
        float offsetY = Math.abs(restrictRect.centerY() - mCy);
        RectF rectF = new RectF(left, top, right, bottom);
        rectF.offset(offsetX, offsetY);
        return rectF;
    }

    private Bitmap createSpotBitmap(String data) {
        Bitmap bitmap = Bitmap.createBitmap(
                (int) mSpotRadius * 2,
                (int) mSpotRadius * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.rgb(235, 160, 160));
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        paint.setTextSize(80);
        paint.setColor(Color.BLACK);
        canvas.drawText(data, 0, 80, paint);
        return bitmap;
    }

    class Spot {
        Matrix matrix;//变形
        Paint paint;
        BitmapShader bitmapShader;
        Bitmap bitmap;
        RectF animRectF;
        RectF originRectF;//供点击判断区域用，回调
        boolean isAnimating;

        public void recycle() {
            this.matrix = null;
            this.paint.setShader(null);
            this.paint = null;
            this.bitmapShader.setLocalMatrix(null);
            this.bitmapShader = null;
            this.animRectF = null;
        }
    }

    public void recycle() {
        for (int i = 0; i < mSpots.size(); i++) {
            Spot spot = mSpots.get(i);
            spot.bitmap.recycle();
            spot.bitmap = null;
            spot.originRectF = null;
        }
        mSpots.clear();
    }
}
