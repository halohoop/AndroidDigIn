package com.halohoop.androiddigin.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.halohoop.androiddigin.R
import com.halohoop.wavefloatview.WaveSurfaceWithTextsViewKotlin

/**
 * Created by Pooholah on 2017/7/13.
 */
class WaveWithTextsFragmentKt : ShowFragment() {

    companion object {
        @JvmStatic fun newInstance(resId: Int) : WaveWithTextsFragmentKt {
            val args = Bundle()
            args.putInt(ShowFragment.LAYOUT_ID_KEY, resId)
            val fragment = WaveWithTextsFragmentKt()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var wav: WaveSurfaceWithTextsViewKotlin;
    lateinit var progress: SeekBar;
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        wav = view?.findViewById(R.id.wav) as WaveSurfaceWithTextsViewKotlin
        progress = view?.findViewById(R.id.progress) as SeekBar
        progress.max = WaveSurfaceWithTextsViewKotlin.MAX_SPEED.toInt()
        progress.progress = 10
        progress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val value = progress.toFloat()
                wav.mSpeed = if (value<=0) 1F else value
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        /*
        val viewGroup = findViewById(android.R.id.content) as ViewGroup
        view.setOnClickListener {
            val progressBar = ProgressBar(this)
            val layoutParams = FrameLayout.LayoutParams(200, 200, Gravity.CENTER)
            viewGroup.addView(progressBar,layoutParams)
        }*/
        view.setOnLongClickListener {
            wav.text = "中abcdefghijkl文"
            true
        }
        return view
    }

}