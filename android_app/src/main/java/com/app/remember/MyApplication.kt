package com.app.remember

import android.app.Application
import com.app.dependencies.data.dao.Database
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    private val provider by lazy {
        DependencyProvider(appContext = this)
    }

    override fun onCreate() {
        super.onCreate()

        Database.init(context = this)

        startKoin {
            androidContext(this@MyApplication)
            modules(provider.module)
        }
    }
}