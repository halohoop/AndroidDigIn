package com.halohoop.androiddigin.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by Pooholah on 2017/5/5.
 */

public class Utils {
    private final static String TAG = "HalohoopAndroidDigIn---";
    private final static boolean DEGUB = true;

    public static void log(String s) {
        if (DEGUB) {
            Log.i(TAG, TAG + s);
        }
    }

    private final static float[] INVERT_MODE = {
            -1, 0, 0, 1, 0,//R
            0, -1, 0, 1, 0,//G
            0, 0, -1, 1, 0,//B
            0, 0, 0, 1, 0  //A
    };

    public static Drawable handleDrawable(Resources res, Bitmap bitmap) {
        Bitmap newBitmap = getBitmap(bitmap);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(res, newBitmap);
        return bitmapDrawable;
    }

    @NonNull
    private static Bitmap getBitmap(Bitmap bitmap) {
        return getBitmap(bitmap, 1);
    }

    @NonNull
    private static Bitmap getBitmap(Bitmap bitmap, int scaleRatio) {
        Bitmap newBitmap =
                Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix(INVERT_MODE);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
//        Log.i(TAG, "getBitmap: " + newBitmap.getWidth() + " " + newBitmap.getHeight());
        newBitmap = Bitmap.createScaledBitmap(newBitmap,
                newBitmap.getWidth() / scaleRatio, newBitmap.getHeight() / scaleRatio, true);
//        Log.i(TAG, "getBitmap: " + newBitmap.getWidth() + " " + newBitmap.getHeight());
        return newBitmap;
    }

    public static Bitmap handleBitmap(Resources res, Bitmap bitmap) {
        return getBitmap(bitmap);
    }

}
