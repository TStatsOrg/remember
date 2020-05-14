package com.app.remember

import android.app.Application
//import com.app.shared.observ.InfiniteEmitter2
//import com.app.shared.observ.ObservableEmitter2
//import com.app.shared.observ.SimpleObserver2
import com.app.shared.observ.map
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

//        val emitter = ObservableEmitter2<Int>()
//        val observer1 = emitter.addObserver()
//            .map {
//                "String value is $it"
//            }
//            .collect {
//                MLogger.log("GABBOX => Observer 1 => Collected: $it")
//            }
//        val observer2 = emitter.addObserver()
//            .map {
//                it * 2
//            }
//            .collect {
//                MLogger.log("GABBOX => Observer 2 => Collected: $it")
//            }
//
//        MLogger.log("GABBOX Total observers => ${emitter.observers.size}")
//
//        emitter.emit(2)
//        emitter.emit(3)
//        emitter.detach(observer = observer2)
//        emitter.emit(5)
//
//        MLogger.log("GABBOX Total observers => ${emitter.observers.size}")
//
//        MLogger.log("GABBOX $observer1")
//        MLogger.log("GABBOX $observer2")
//        MLogger.log("GABBOX ${emitter.observers}")
    }
}