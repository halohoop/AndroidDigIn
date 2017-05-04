package com.halohoop.androiddigin.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.halohoop.androiddigin.R;

/**
 * Created by Pooholah on 2017/5/4.
 */

public class MagnifierView extends View {
    //放大倍数
    private int FACTOR = 3;
    //放大镜尺寸
    private int HALF_RADIUS = 150;
    private int WIDTH = HALF_RADIUS * 2;
    //放大镜放大的位置
    private RectF mZoomRect = new RectF();
    //放大镜展示的位置
    private RectF mMagnifierRect = new RectF();
    private PointF[] mTangentPointFs = null;
    private ShapeDrawable mShapeDrawable;
    private Matrix mMatrix;
    private BitmapShader mBitmapShader;
    private Bitmap bitmap;
    private Bitmap scaledBitmap;
    private int mOffsetMagnifier = 250;
    private Paint mPaint;

    public MagnifierView(Context context) {
        this(context, null);
    }

    public MagnifierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagnifierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.pic)).getBitmap();
        scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * FACTOR, bitmap.getHeight() * FACTOR,
                true);
//        bitmap.recycle();
        mBitmapShader = new BitmapShader(scaledBitmap,
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        mShapeDrawable = new ShapeDrawable(new OvalShape());
        mShapeDrawable.setBounds(0, 0, WIDTH, WIDTH);
        mShapeDrawable.getPaint().setShader(mBitmapShader);
        mMatrix = new Matrix();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        //移动到左上角，再往右下移动半径长度
        mMatrix.setTranslate(HALF_RADIUS - x * FACTOR, HALF_RADIUS - y * FACTOR);//难点
        mShapeDrawable.getPaint().getShader().setLocalMatrix(mMatrix);
        float realZoomRadius = HALF_RADIUS / 3.0f;
        //放大镜放大的位置
        mZoomRect.set(x - realZoomRadius, y - realZoomRadius, x + realZoomRadius, y + realZoomRadius);
        //放大镜展示的位置
        mMagnifierRect.set(x - HALF_RADIUS, y - HALF_RADIUS - mOffsetMagnifier,
                x + HALF_RADIUS, y + HALF_RADIUS - mOffsetMagnifier);
        mShapeDrawable.setBounds(new Rect((int) mMagnifierRect.left, (int) mMagnifierRect.top,
                (int) mMagnifierRect.right, (int) mMagnifierRect.bottom));
        //获得大小圆的切线点
        mTangentPointFs = getTangentPointFs(mTangentPointFs, mZoomRect, mMagnifierRect);
        invalidate();
        return true;
    }

    /**
     * 获取两条切线 也即是 四个点 0 - 1，2 - 3
     *
     * @param tangentPointFs
     * @param zoomRect
     * @param magnifierRect
     * @return
     */
    private PointF[] getTangentPointFs(PointF[] tangentPointFs, RectF zoomRect, RectF magnifierRect) {
        float smallCx = zoomRect.centerX();
        float smallCy = zoomRect.centerY();
        float bigCx = magnifierRect.centerX();
        float bigCy = magnifierRect.centerY();
        float smallRadius = zoomRect.width() / 2.0f;
        float bigRadius = magnifierRect.width() / 2.0f;
        float cos = Math.abs(bigRadius - smallRadius) / Math.abs(smallCy - bigCy);
        float bigDeltaY = cos * bigRadius;
        float bigDeltaX = (float) Math.sqrt(bigRadius * bigRadius - bigDeltaY * bigDeltaY);
        float smallDeltaY = cos * smallRadius;
        float smallDeltaX = (float) Math.sqrt(smallRadius * smallRadius - smallDeltaY * smallDeltaY);
        PointF[] pointFs = null;
        if (tangentPointFs != null) {
            pointFs = tangentPointFs;
        } else {
            pointFs = new PointF[4];
        }
        //小圆左下角切点
        pointFs[0] = new PointF();
        pointFs[0].x = smallCx - smallDeltaX;
        pointFs[0].y = smallCy + smallDeltaY;
        //大圆左下角切点
        pointFs[1] = new PointF();
        pointFs[1].x = bigCx - bigDeltaX;
        pointFs[1].y = bigCy + bigDeltaY;
        //小圆右下角切点
        pointFs[2] = new PointF();
        pointFs[2].x = smallCx + smallDeltaX;
        pointFs[2].y = smallCy + smallDeltaY;
        //大圆右下角切点
        pointFs[3] = new PointF();
        pointFs[3].x = bigCx + bigDeltaX;
        pointFs[3].y = bigCy + bigDeltaY;
        return pointFs;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
//        canvas.drawBitmap(scaledBitmap, 0, 0, null);
        mShapeDrawable.draw(canvas);
        canvas.drawCircle(mZoomRect.centerX(), mZoomRect.centerY(), mZoomRect.width() / 2.0f, mPaint);
        if (mTangentPointFs != null) {
            canvas.drawLine(mTangentPointFs[0].x, mTangentPointFs[0].y,
                    mTangentPointFs[1].x, mTangentPointFs[1].y, mPaint);
            canvas.drawLine(mTangentPointFs[2].x, mTangentPointFs[2].y,
                    mTangentPointFs[3].x, mTangentPointFs[3].y, mPaint);
        }
    }
}
