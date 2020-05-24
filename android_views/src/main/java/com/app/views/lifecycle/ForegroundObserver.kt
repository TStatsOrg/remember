package com.app.views.lifecycle

import androidx.lifecycle.LifecycleObserver

interface ForegroundObserver: LifecycleObserver {

    fun observeAppInForeground(callback: () -> Unit)
    fun observeAppInBackground(callback: () -> Unit)
}