package com.halohoop.androiddigin.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

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


    /**
     * 单例吐司---start
     */
    private static Toast toast;

    public static void showToast(Context context, String string) {
        if(toast == null){
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(string);
        toast.show();
    }
    /**
     * 单例吐司---end
     */

    /**
     * 从颜色得到另一个对比度高的颜色，其实就是给出黑白两种色，
     * 不过传入的颜色如果较暗，那就是白色，如果较亮那就是黑色
     * @param color
     * @return
     */
    public static int getComplimentColor(@ColorInt int color) {
        // get existing colors
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int blue = Color.blue(color);
        int green = Color.green(color);

        // find compliments
        red = (~red) & 0xff;
        blue = (~blue) & 0xff;
        green = (~green) & 0xff;

        return Color.argb(alpha, red, green, blue);
    }



    public static int randomAColor(Random random){
        return Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255));
    }

    public static int getContrastColor(@ColorInt int color) {
        // Counting the perceptive luminance - human eye favors green color...
        double a = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;

        int d;
        if (a < 0.5) {
            d = 0; // bright colors - black font
        } else {
            d = 255; // dark colors - white font
        }

        return Color.rgb(d, d, d);
    }

    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
