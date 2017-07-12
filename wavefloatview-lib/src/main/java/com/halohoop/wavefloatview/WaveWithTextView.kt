package com.halohoop.wavefloatview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import android.support.annotation.MainThread
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import java.util.*

/**
 * Created by Pooholah on 2017/7/11.
 */

class WaveWithTextView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {

    //    var text = javaClass.name
    var text: String = "Halohoop"
        set(value) {
            field = value
            val textPosMidX = mMidX;
            updateTextsConfigs(value, textPosMidX)
        }
        get

    private var path: Path
    private var paint: Paint
    private var textPaint: TextPaint

    /**
     * 中心点
     */
    private var mMidX = 0f;
    private var mMidY = 0f;

    private var mWaveHeight = 50f;
    private var mHalfWaveWidth = 300f;
    private var mWaveCount = 2;//一高一低的波峰和波谷为一个波浪

    private lateinit var mRegion: Region
    private var mHowWidthOfTexts = 0f
    private lateinit var mEveryLetterWidths: FloatArray
    private var mTextPositionHelperRegions: ArrayList<Region>? = null

    @ColorInt
    private val WAVE_COLOR = Color.parseColor("#aa00FFFF")
    @ColorInt
    private val TEXT_COLOR = Color.BLACK

    /**first constructor**/
    constructor(context: Context?) : this(context, null) {}

    /**secondary constructor**/
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {}

    //主构造函数的函数体
    /**main constructor**/
    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)

        path = Path();
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        paint.color = WAVE_COLOR
        textPaint.color = TEXT_COLOR
        textPaint.textSize = 100f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mMidX = (w shr 1).toFloat()
        mMidY = (h shr 1).toFloat()

        mWaveCount = calculateWaveCount(w.toFloat(), mHalfWaveWidth * 2f)

        mRegion = Region()

        val textPosMidX = mMidX;
        val newText = text;
        updateTextsConfigs(newText, textPosMidX)
    }

    /**
     * @param newText 新的文字
     * @param textPosMidX 设置文字的中心所在位置
     */
    @MainThread
    private fun updateTextsConfigs(newText: String, textPosMidX: Float) {

        fun getEveryLetterWidth(text: String) : FloatArray{
            mEveryLetterWidths = FloatArray(text.length)
            textPaint.getTextWidths(text, 0, text.length, mEveryLetterWidths)
            return mEveryLetterWidths
        }

        fun getHowWidthOfTexts(everyLetterWidths: FloatArray): Float {
            mHowWidthOfTexts = 0f;
            everyLetterWidths.forEach { mHowWidthOfTexts += it }
            return mHowWidthOfTexts
        }

        /**
         * initialize TextPositionHelperRegions
         */
        fun updateTextPositionHelperRegions(textPosMidX: Float = 0f, howWidthOfTexts: Float,
                                                    everyLetterWidths: FloatArray) {
            val startX = textPosMidX - howWidthOfTexts / 2
            val tmpEveryLetterWidths = everyLetterWidths
            var tmpHowWidthOfTexts = howWidthOfTexts
            val lettersCount = tmpEveryLetterWidths.size

            if (mTextPositionHelperRegions != null) {
                mTextPositionHelperRegions!!.clear()
                mTextPositionHelperRegions = null
            }
            mTextPositionHelperRegions = ArrayList<Region>()

//            val offset = tmpEveryLetterWidths[tmpEveryLetterWidths.size-1] / 2f
            for (i in lettersCount - 1 downTo 0) {
                if (tmpHowWidthOfTexts < 0) tmpHowWidthOfTexts = 0f
                tmpHowWidthOfTexts -= tmpEveryLetterWidths[i]
                val region = Region((startX - 1 + tmpHowWidthOfTexts).toInt(), 0,
                        (startX + tmpHowWidthOfTexts).toInt(), measuredHeight)
                mTextPositionHelperRegions!!.add(0, region)
            }
        }

        val everyLetterWidths = getEveryLetterWidth(newText)
        //get how width of texts
        val howWidthOfTexts = getHowWidthOfTexts(everyLetterWidths)
        //initialize TextPositionHelperRegions
        updateTextPositionHelperRegions(textPosMidX, howWidthOfTexts, everyLetterWidths)
    }

    /**
     * 适配横竖屏幕
     */
    fun calculateWaveCount(viewWidth: Float, waveWidth: Float): Int {
        if (waveWidth >= viewWidth) {
            return 2//至少需要使用两个波浪
        }
        return (viewWidth / waveWidth).toInt() + 1
    }

    var flowAnimator: ValueAnimator? = null
    var mDx: Float = 0.0f;

    fun startFlow() {
        stopFlow()
        flowAnimator = ValueAnimator.ofFloat(0f, mHalfWaveWidth * 2)
                .setDuration(1000)
        flowAnimator!!.setRepeatMode(ValueAnimator.RESTART)
        flowAnimator!!.setRepeatCount(ValueAnimator.INFINITE)
        flowAnimator!!.interpolator = LinearInterpolator()
        flowAnimator!!.addUpdateListener {
            mDx = it.animatedValue as Float
            this@WaveWithTextView.invalidate(0, (mMidY - mWaveHeight).toInt(), measuredHeight, (mMidY + mWaveHeight).toInt())
        }

        flowAnimator!!.start()
    }

    fun stopFlow() {
        if (flowAnimator != null && flowAnimator!!.isRunning && flowAnimator!!.isStarted) {
            flowAnimator!!.cancel()
            flowAnimator = null
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (DEBUG) {
            canvas?.drawLine((measuredWidth shr 1).toFloat(), 0f,
                    (measuredWidth shr 1).toFloat(), measuredHeight.toFloat(), paint)
        }

        drawTexts(canvas)
        drawWave(canvas)

    }

    private fun drawWave(canvas: Canvas?) {
        paint.color = WAVE_COLOR
        val quaterWaveWidth = mHalfWaveWidth / 2f;

        path.rewind()

        val dx = mDx;
        path.moveTo(0f - mHalfWaveWidth * 2f + dx, mMidY)

        for (i in 0..mWaveCount) {
            path.rQuadTo(quaterWaveWidth, mWaveHeight, mHalfWaveWidth, 0f)
            path.rQuadTo(quaterWaveWidth, -mWaveHeight, mHalfWaveWidth, 0f)
        }

        path.lineTo(measuredWidth.toFloat(), measuredHeight.toFloat())
        path.lineTo(0f, measuredHeight.toFloat())
        path.close()

        canvas?.drawPath(path, paint)
    }

    private fun drawTexts(canvas: Canvas?) {
        textPaint.color = TEXT_COLOR
        var i = 0
        val toCharArray = text!!.toCharArray()
        mTextPositionHelperRegions?.forEach {
            mRegion.setPath(path, it)
            if (DEBUG) {
                //debug
                canvas?.drawRect(it.bounds, textPaint)
            }

            //        //get text postion mRegion
            canvas?.drawText(toCharArray[i] + "", it.bounds.left.toFloat(), mRegion.bounds.top.toFloat(), textPaint)
            if (DEBUG && i == 0) {
                canvas?.drawText(text, it.bounds.left.toFloat(), mRegion.bounds.top.toFloat() - 100, textPaint)
            }
            i++
        }
    }

    private val DEBUG = true;

}