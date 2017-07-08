package com.halohoop.androiddigin.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Pooholah on 2017/7/8.
 */

public class WaveViewTmp extends View {

    private static final String TAG = "WaveViewTmp";

    private static final int SAMPLE_SIZE = 128;

    private float[] mSamplingX;//采样点
    private float[] mMapX;
    private int mWidth;
    private int mHeight;
    private int mCenterHeight;
    private int mAmplitude;//振幅

    //波峰和两条路径交叉点的记录，包括起点和终点，用于绘制渐变
    private final float[][] mCrestAndCrossPints = new float[9][];

    private final RectF rectF = new RectF();

    private final Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private final int mBgColor = Color.rgb(24, 33, 41);
    private final int mCenterColor = Color.argb(64, 255, 255, 255);
    private long startTime = System.currentTimeMillis();

    private final Paint mPaint = new Paint();
    private final Path mFirstPath = new Path();
    private final Path mSecondPath = new Path();
    private final Path mCenterPath = new Path();

    public WaveViewTmp(Context context) {
        this(context, null);
    }

    public WaveViewTmp(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveViewTmp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        for (int i = 0; i < 9; i++) {
            mCrestAndCrossPints[i] = new float[2];
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mSamplingX == null) {
            mWidth = canvas.getWidth();
            mHeight = canvas.getHeight();
            mCenterHeight = mHeight >> 1;
            mAmplitude = mWidth >> 3;

            mSamplingX = new float[SAMPLE_SIZE + 1];
            mMapX = new float[SAMPLE_SIZE + 1];
            float gap = mWidth / (float) SAMPLE_SIZE;
            float x;
            for (int i = 0; i < SAMPLE_SIZE; i++) {
                x = i * gap;
                mSamplingX[i] = x;
                mMapX[i] = (x / (float) mWidth) * 4 - 2;
            }
        }
        canvas.drawColor(mBgColor);
        //重置所有path并且移动到起点
        mFirstPath.rewind();
        mSecondPath.rewind();
        mCenterPath.rewind();

        mFirstPath.moveTo(0, mCenterHeight);
        mSecondPath.moveTo(0, mCenterHeight);
        mCenterPath.moveTo(0, mCenterHeight);

        //当前时间的偏移量，通过该偏移量使得每次绘制都向右偏移
        //让画面动起来，如果希望速度快一点，可以调小分母

        float offset = (System.currentTimeMillis() - startTime) / 500f;
        Log.i(TAG, "onDraw offset: " + offset);
        //提前声明各种临时参数
        float x;
        float[] xy;

        //波形函数的值，包括上一点，当前点和下一点
        float curV = 0;
        float lastV = 0;
        //计算第一个采样点的y值
        float nextV = (float) (mAmplitude * calcValue(mMapX[0], offset, 4));
        //波形函数的绝对值，用于筛选波峰和交错点，来绘制渐变效果
        float absLastV, absCurV, absNextV;
        //上一个筛选出的点是波峰还是交错点
        boolean lastIsCrest = false;
        //筛选出的波峰和交叉点的数量，包括起点和终点
        int crestAndCrossCount = 0;

        for (int i = 0; i < SAMPLE_SIZE; i++) {
            x = mSamplingX[i];

            lastV = curV;
            curV = nextV;

            ///计算下一个采样点的y坐标
            nextV = i < SAMPLE_SIZE ? (float) (mAmplitude * calcValue(mMapX[i + 1], offset, 4)) : 0;

            //连接路径
            mFirstPath.lineTo(x, mCenterHeight + curV);
            mSecondPath.lineTo(x, mCenterHeight - curV);
            mCenterPath.lineTo(x, mCenterHeight + curV / 5f);

            //记录极值点
            absLastV = Math.abs(lastV);
            absCurV = Math.abs(curV);
            absNextV = Math.abs(nextV);

            //算斜率
            if (i == 0 || i == SAMPLE_SIZE || (lastIsCrest && absCurV < absNextV && absCurV < absLastV)) {
                xy = mCrestAndCrossPints[crestAndCrossCount++];
                xy[0] = x;//为了画矩形
                xy[1] = 0;//为了画矩形
                lastIsCrest = false;
            } else if (!lastIsCrest && absCurV > absLastV && absCurV > absNextV) {
                xy = mCrestAndCrossPints[crestAndCrossCount++];
                xy[0] = x;//为了画矩形
                xy[1] = curV;//为了画矩形
                lastIsCrest = true;
            }
        }

        mFirstPath.lineTo(mWidth, mCenterHeight);
        mSecondPath.lineTo(mWidth, mCenterHeight);
        mCenterPath.lineTo(mWidth, mCenterHeight);

        //记录layer
        int saveCount = canvas.saveLayer(0, 0, mWidth, mHeight, null,
                Canvas.ALL_SAVE_FLAG);

        //填充上下两条正弦函数
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.CYAN);
        mPaint.setStrokeWidth(1);
        canvas.drawPath(mFirstPath, mPaint);
        canvas.drawPath(mSecondPath, mPaint);
        //到此三根线的轮廓画出来了

        //绘制渐变
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(mXfermode);

        float startX, crestY, endX;
        for (int i = 2; i < crestAndCrossCount; i += 2) {
            //每隔两个点可绘制一个矩形，这里先计算矩形的参数
            startX = mCrestAndCrossPints[i - 2][0];
            crestY = mCrestAndCrossPints[i - 1][1];
            endX = mCrestAndCrossPints[i][0];

            //crest有正负 无需计算渐变是从上到下还是从下到上
            mPaint.setShader(new LinearGradient(0, mCenterHeight + crestY,
                    0, mCenterHeight - crestY, Color.BLUE,
                    Color.GREEN, Shader.TileMode.CLAMP));
            rectF.set(startX, mCenterHeight + crestY, endX, mCenterHeight - crestY);
            canvas.drawRect(rectF, mPaint);
        }

        //清理一下
        mPaint.setShader(null);
        mPaint.setXfermode(null);

        //叠加layer，因为使用了SRC_IN的模式所以只会保留波形渐变重合的地方
        canvas.restoreToCount(saveCount);
        //绘制上弦线
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        canvas.drawPath(mFirstPath, mPaint);
        //绘制下弦线
        mPaint.setColor(Color.GREEN);
        canvas.drawPath(mSecondPath, mPaint);

        //绘制中间线
        mPaint.setColor(mCenterColor);
        canvas.drawPath(mCenterPath, mPaint);
        super.onDraw(canvas);
        postInvalidateDelayed(16);
    }

    /**
     * 算Y轴坐标
     *
     * @param mapX
     * @param offset
     * @return
     */
    private double calcValue(float mapX, float offset,int factor) {
        offset %= 2;
        double sinFun = Math.sin(0.75 * Math.PI * mapX - offset * Math.PI);
        double recessionFunc = Math.pow(factor / (4 + Math.pow(mapX, 4)), 2.5);
        return sinFun * recessionFunc;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
