package com.app.shared.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * The main app scope, for things that can't be put in a ViewModle scope
 */
actual val AppScope: CoroutineScope = GlobalScope

/**
 * Different dispatchers: main, IO (network) and Default (everything else)
 */
actual val MainDispatcher: CoroutineDispatcher = Dispatchers.Main
actual val IODispatcher: CoroutineDispatcher = Dispatchers.IO
actual val DefaultDispatcher: CoroutineDispatcher = Dispatchers.Default

/**
 * Function that can provide a context
 */
actual fun provideViewModelScope(): CoroutineScope = MyViewModelScope()

/**
 * A custom coroutine scope, good for a view model
 */
internal class MyViewModelScope: CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = MainDispatcher + Job()
}