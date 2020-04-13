package com.app.shared.data.capture

interface RawDataCapture {
    fun capture(data: (String?) -> Unit)
}