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

        val emitter = InfiniteEmitter2<Int>()

        val observer1 = SimpleObserver2<Int> { value ->
            MLogger.log("GABBOX: Observer 1 => $value")
        }

        val observer2 = SubscribingObserver2<Int>()
        observer2.subscriber.map {
            it * 2
        }.collect { value ->
            MLogger.log("GABBOX: Observer 2 => $value")
        }

        emitter.add(observer = observer1)
        emitter.add(observer = observer2)

        MLogger.log("GABBOX: Number of remaining observers ${emitter.observers.size}")

        emitter.emit(value = 1)
        emitter.emit(value = 2)
        emitter.emit(value = 5)

        emitter.remove(observer1)
        emitter.remove(observer2)

        MLogger.log("GABBOX: Number of remaining observers ${emitter.observers.size}")
    }
}