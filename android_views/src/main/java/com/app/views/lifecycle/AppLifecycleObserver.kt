package com.app.views.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.app.shared.observ.InfiniteEmitter

class AppLifecycleObserver: ForegroundObserver {

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    private val backgroundEmitter = InfiniteEmitter<Boolean>()
    private val foregroundEmitter = InfiniteEmitter<Boolean>()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() = backgroundEmitter.emit(value = true)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() = foregroundEmitter.emit(value = true)

    override fun observeAppInBackground(callback: () -> Unit) {
        backgroundEmitter.observe().collect { callback() }
    }

    override fun observeAppInForeground(callback: () -> Unit) {
        foregroundEmitter.observe().collect { callback() }
    }
}