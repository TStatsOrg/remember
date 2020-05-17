package com.app.remember

import android.app.Application
import com.app.shared.observ.*
import com.app.shared.utils.MLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    private val provider by lazy {
        AppDependencyProvider(appContext = this)
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(provider.module)
        }

        val emitter = InfiniteEmitter2<Int?>()

        val observer1 = SimpleObserver2<Int?>().collect {
            MLogger.log("GABBOX: Observer 1 => $it")
        }

        val observer2 = SimpleObserver2<Int?>()
        observer2.filterNotNull().map { it * 2 }.collect {
            MLogger.log("GABBOX: Observer 2 => $it")
        }

        val observer3 = SimpleObserver2<Int?>()
        observer3.filterNotNull().map { it * 3 }.filter { it > 12 }.collect {
            MLogger.log("GABBOX: Observer 3 => $it")
        }

        emitter.add(observer1)
        emitter.add(observer2)
        emitter.add(observer3)

        emitter.emit(value = 1)
        emitter.emit(value = null)
        emitter.emit(value = 5)

        MLogger.log("GABBOX: Number of remaining observers ${emitter.observers.size}")

        emitter.remove(observer1)
        emitter.remove(observer2)
        emitter.remove(observer3)

        MLogger.log("GABBOX: Number of remaining observers ${emitter.observers.size}")
    }
}