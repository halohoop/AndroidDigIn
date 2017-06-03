package com.halohoop.androiddigin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class KotlinMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_main)

        var s :String = "Halohoop"
        var name :String? = null
//        val len = name!!.length;//kotlin null pointer exception
        val length = name?.length ?: -1;//nullable
        Log.i(s,""+length)
    }
}
