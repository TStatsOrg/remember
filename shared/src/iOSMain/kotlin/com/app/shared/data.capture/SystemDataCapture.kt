package com.app.shared.data.capture

actual class SystemDataCapture: DataCapture {
    actual override fun unbox(): DataCapture.Item {
        return DataCapture.Item.Unknown
    }
}