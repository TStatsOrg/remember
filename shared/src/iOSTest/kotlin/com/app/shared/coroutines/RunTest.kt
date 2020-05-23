package com.app.shared.coroutines

import kotlinx.coroutines.CoroutineScope

/**
 * Workaround to use suspending functions in unit tests
 */
actual fun runTest(block: suspend (scope : CoroutineScope) -> Unit) = Unit