package com.app.shared.coroutines

import kotlinx.coroutines.*
import platform.darwin.*
import kotlin.coroutines.CoroutineContext

/**
 * A coroutine scope is a lightweight box around a Context.
 * A context usually will contain a dispatcher and a job.
 */
@InternalCoroutinesApi
class iOSScope : CoroutineScope {
    private val dispatcher = MainDispatcher
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher + job
}