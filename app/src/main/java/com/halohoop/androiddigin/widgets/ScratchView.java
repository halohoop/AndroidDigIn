package com.halohoop.androiddigin.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.halohoop.androiddigin.R;

import java.io.InputStream;

/**
 * Created by Pooholah on 2017/5/15.
 */

public class ScratchView extends View {

    private Paint mScratchPaint;
    private Bitmap mBgBitmap;
    private Bitmap mScratchBitmap;
    private Path mScratchPath;
    private float mPreX;
    private float mPreY;
    private PorterDuffXfermode mXfermode;
    private Rect rect;

    public ScratchView(Context context) {
        this(context, null);
    }

    public ScratchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScratchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mScratchPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScratchPaint.setStyle(Paint.Style.STROKE);
        mScratchPaint.setStrokeWidth(60);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
        mScratchPath = new Path();
        rect = new Rect(450, 704, 1060, 1002);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBgBitmap = createBgBitmap(w, h, "中奖啦!", Color.CYAN);
        mScratchBitmap = createScratchBitmap(w, h, Color.GRAY);
    }

    private Bitmap createScratchBitmap(int w, int h, int color) {
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(color);
        return bitmap;
    }

    private Bitmap createBgBitmap(int w, int h, String text, int textColor) {
        Bitmap bgPicBitmap = getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bgPicBitmap, 0, 0, null);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GRAY);
        canvas.drawRect(rect,paint);
        paint.setColor(textColor);
        float textSize = 150;
        paint.setTextSize(textSize);
        int cx = w / 2;
        int cy = h / 2;
        float measureText = paint.measureText(text);
        //将文字画在中间
        canvas.drawText(text, cx - measureText / 2, cy + textSize / 2, paint);
        canvas = null;
        paint = null;
        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (rect.contains((int) event.getX(), (int) event.getY())) {
            showToast(getContext(),"要看此区域请付费！");
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScratchPath.moveTo(event.getX(), event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX + event.getX()) / 2;
                float endY = (mPreY + event.getY()) / 2;
                mScratchPath.quadTo(mPreX, mPreY, endX, endY);
                mPreX = event.getX();
                mPreY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBgBitmap, 0, 0, null);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mScratchBitmap, 0, 0, null);
        mScratchPaint.setXfermode(mXfermode);
        canvas.drawPath(mScratchPath, mScratchPaint);
        mScratchPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    private Bitmap getBitmap(){
        byte[] bytes = "a".getBytes();
        byte[] bitmapBytes = new byte[159708];//文件本来的大小
        int pointer = 0;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.xyjy);
            byte[] buf = new byte[512 + bytes.length];
            int len;
            while ((len = inputStream.read(buf)) != -1) {
//                outputStream.write(buf, bytes.length, len - bytes.length);
                for (int i = bytes.length; i < bytes.length+(len - bytes.length); i++) {
                    bitmapBytes[pointer++] = buf[i];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 1400, 1967, true);
        bitmap.recycle();
        bitmap = null;
        return scaledBitmap;
    }

    private static Toast toast;

    public static void showToast(Context context, String string) {
        if(toast == null){
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(string);
        toast.show();
    }
}
