package com.app.shared.utils

import android.util.Log

actual class InternalLogger {
    actual fun log(message: String) {
        Log.d("MLogger", message)
    }
}