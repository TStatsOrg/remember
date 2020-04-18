package com.app.shared.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

/**
 * The main app scope, for things that can't be put in a ViewModle scope
 */
expect val AppScope: CoroutineScope

/**
 * Different dispatchers: main, IO (network) and Default (everything else)
 */
expect val MainDispatcher: CoroutineDispatcher
expect val IODispatcher: CoroutineDispatcher
expect val DefaultDispatcher: CoroutineDispatcher

/**
 * Function that can provide a context
 */
expect fun provideViewModelScope(): CoroutineScope