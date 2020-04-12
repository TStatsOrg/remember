package com.app.remember

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    private val provider by lazy {
        DependencyProvider(appContext = this)
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(provider.module)
        }
    }
}