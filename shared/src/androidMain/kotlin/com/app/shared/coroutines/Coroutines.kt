package com.app.shared.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

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