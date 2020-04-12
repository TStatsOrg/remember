package com.app.shared.data.capture

expect class SystemDataCapture: DataCapture {
    override fun unbox(): DataCapture.Item
}