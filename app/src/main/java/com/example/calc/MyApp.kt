package com.example.calc

import android.app.Application
import android.content.Context

class MyApp : Application() {

    companion object {
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
