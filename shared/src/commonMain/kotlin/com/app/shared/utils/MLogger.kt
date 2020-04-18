package com.app.shared.utils

object MLogger {

    private val internalLogger = InternalLogger()

    fun log(message: String) = internalLogger.log(message)
}

expect class InternalLogger constructor() {
    fun log(message: String)
}