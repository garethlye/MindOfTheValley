package com.example.mindofthevalley

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MindValleyApplication : Application() {

    companion object {
        lateinit var instance: MindValleyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}