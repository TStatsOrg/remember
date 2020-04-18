package com.app.shared.coroutines

import kotlinx.coroutines.*
import platform.darwin.*
import kotlin.coroutines.CoroutineContext

/**
 * The iOS Dispatcher dispatches on a different thread and always returns on the main thread.
 * This one also implements the Delay interface, as presented here: https://github.com/Kotlin/kotlinx.coroutines/issues/470
 */
@ExperimentalCoroutinesApi
@UseExperimental(InternalCoroutinesApi::class)
class iOSDispatcher: CoroutineDispatcher(), Delay {

    companion object {
        private const val A_THOUSAND_OF_A_SECOND_IN_NANOSECONDS = 1_000_000
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) {
            try {
                block.run()
            } catch (err: Throwable) {
                throw err
            }
        }
    }

    /**
     * Resumes a delay in the coroutine after a set time in nanoseconds
     *
     * Math:
     *  => timeMillis = 60_000 milliseconds = 60 seconds = 1 minute
     *  => A_THOUSAND_OF_A_SECOND_IN_NANOSECONDS = 1_000_000 nanoseconds = 0.001 seconds
     *  => delta = 60_000_000_000 nanoseconds = 60 seconds = 1 minute
     */
    @InternalCoroutinesApi
    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, timeMillis * A_THOUSAND_OF_A_SECOND_IN_NANOSECONDS), dispatch_get_main_queue()) {
            try {
                with(continuation) {
                    resumeUndispatched(Unit)
                }
            } catch (err: Throwable) {
                throw err
            }
        }
    }

    /**
     * Invoke on timeout
     *
     * Math:
     *  => timeMillis = 60_000 milliseconds = 60 seconds = 1 minute
     *  => A_THOUSAND_OF_A_SECOND_IN_NANOSECONDS = 1_000_000 nanoseconds = 0.001 seconds
     *  => delta = 60_000_000_000 nanoseconds = 60 seconds = 1 minute
     */
    @InternalCoroutinesApi
    override fun invokeOnTimeout(timeMillis: Long, block: Runnable): DisposableHandle {
        val handle = object : DisposableHandle {
            var disposed = false
                private set

            override fun dispose() {
                disposed = true
            }
        }
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, timeMillis * A_THOUSAND_OF_A_SECOND_IN_NANOSECONDS), dispatch_get_main_queue()) {
            try {
                if (!handle.disposed) {
                    block.run()
                }
            } catch (err: Throwable) {
                throw err
            }
        }

        return handle
    }

}