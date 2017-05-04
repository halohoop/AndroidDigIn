package com.halohoop.androiddigin.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.halohoop.androiddigin.R;

/**
 * Created by Pooholah on 2017/5/4.
 */

public class ColorMatrixView extends View {

    private Bitmap bitmap;
    private Bitmap newBitmap;
    private Canvas canvas;

    public ColorMatrixView(Context context) {
        this(context, null);
    }

    public ColorMatrixView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorMatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.pic)).getBitmap();
        newBitmap = getNewBitmap(this.bitmap, null);
    }

    private Bitmap getNewBitmap(Bitmap bitmap, Paint paint) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        canvas = new Canvas(newBitmap);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    public void change(float[] colors) {//4*5矩阵 length==20
        if (colors == null || colors.length != 20) {
            return;
        }

        ColorMatrix colorMatrix = new ColorMatrix(colors);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        this.newBitmap = getNewBitmap(this.bitmap, paint);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.translate(bitmap.getWidth() + 10, 0);
        canvas.drawBitmap(newBitmap, 0, 0, null);
    }
}
