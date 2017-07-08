package com.halohoop.androiddigin.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.WorkerThread;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Pooholah on 2017/7/7.
 */

public abstract class BaseSurfaceWaveView extends SurfaceView implements SurfaceHolder.Callback {

    private final Object mSurfaceLock = new Object();
//    private RenderThread mRenderThread;

    private RenderThread mRenderThread;

    public BaseSurfaceWaveView(Context context) {
        this(context, null);
    }

    public BaseSurfaceWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseSurfaceWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mRenderThread = new RenderThread(holder);
        mRenderThread.start();//开始绘制
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (mSurfaceLock) {
            mRenderThread.setIsRun(false);
        }
    }

    private class RenderThread extends Thread {

        private static final long SLEEP_TIME = 16;
        private SurfaceHolder mSurfaceHolder;
        private boolean mIsRunning = true;

        public RenderThread(SurfaceHolder holder) {
            this.mSurfaceHolder = holder;
        }

        public void setIsRun(boolean isRun) {
            mIsRunning = isRun;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            while (true) {
                if (!mIsRunning) {
                    return;
                }
                Canvas canvas = mSurfaceHolder.lockCanvas();
                if (canvas!=null) {
                    onRender(canvas, System.currentTimeMillis() - startTime);
                    //绘制完成
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @WorkerThread
    protected abstract void onRender(Canvas canvas, long l);

}
