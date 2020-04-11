package com.app.shared.utils

actual class InternalLogger {
    actual fun log(message: String) {
        print("MLogger: $message")
    }
}