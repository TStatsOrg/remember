package com.app.shared.coroutines

import kotlinx.coroutines.*

/**
 * The main app scope, for things that can't be put in a ViewModle scope
 */
actual val AppScope: CoroutineScope = iOSScope()

/**
 * Different dispatchers: main, IO (network) and Default (everything else)
 */
actual val MainDispatcher: CoroutineDispatcher = iOSDispatcher()
actual val IODispatcher: CoroutineDispatcher = iOSDispatcher()
actual val DefaultDispatcher: CoroutineDispatcher = iOSDispatcher()

/**
 * Function that can provide a context
 */
actual fun provideViewModelScope(): CoroutineScope = iOSScope()