package com.halohoop.wavefloatview

import android.content.Context
import android.graphics.Canvas
import android.support.annotation.WorkerThread
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * Created by Pooholah on 2017/7/7.
 */

abstract class BaseSurfaceWaveWithTextsViewKotlin(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    private var mRenderThread: RenderThread? = null

    init {
        holder.addCallback(this)
    }

    /**first constructor**/
    constructor(context: Context?) : this(context, null) {}

    /**secondary constructor**/
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun surfaceCreated(holder: SurfaceHolder) {
        startLoopDraw()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        lock(lockObj,{
            stopLoopDraw()
        })
    }

    fun startLoopDraw() {
        if (mRenderThread != null && mRenderThread!!.mIsRunning) {
            return
        }
        mRenderThread = RenderThread(holder)
        mRenderThread!!.start()//开始绘制
    }

    fun stopLoopDraw() {
        mRenderThread!!.mIsRunning = false
        mRenderThread = null
    }

    val lockObj = ReentrantLock()

    fun <T> lock(lock: Lock, body: () -> T) {
        lock.lock()
        try {
            body()
        }
        finally {
            lock.unlock()
        }
    }

    private inner class RenderThread(private val mSurfaceHolder: SurfaceHolder) : Thread() {
        var mIsRunning = true

        override fun run() {
            val startTime = System.currentTimeMillis()
            while (true) {
                if (!mIsRunning) {
//                        return@lock
                    Log.i("halohoop", "Halohoop--" + "stop")
                    break
                }
                val canvas = mSurfaceHolder.lockCanvas()
                if (canvas != null) {
                    lock(lockObj, {
                        if (mIsRunning) {
                            onThreadRenderDraw(canvas, System.currentTimeMillis() - startTime)
                        }
                    })
                    //绘制完成
                    mSurfaceHolder.unlockCanvasAndPost(canvas)
                }
                try {
                    Thread.sleep(SLEEP_TIME)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

    }

    companion object {
        private val SLEEP_TIME: Long = 16
    }

    @WorkerThread
    protected abstract fun onThreadRenderDraw(canvas: Canvas, l: Long)

}
