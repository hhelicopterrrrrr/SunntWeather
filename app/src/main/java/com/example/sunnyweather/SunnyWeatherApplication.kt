package com.example.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log

class SunnyWeatherApplication :Application(){
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN="ilyAtMXEQ4vSOnNr"

    }
    override fun onCreate() {
        super.onCreate()
        context=applicationContext

    }
}