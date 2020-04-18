package com.app.shared.data.capture

interface RawDataCapture<Input> {
    fun capture(input: Input, data: (String?) -> Unit)
}