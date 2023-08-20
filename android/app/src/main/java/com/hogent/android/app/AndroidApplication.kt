package com.hogent.android.app

import android.app.Application
import timber.log.Timber

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
