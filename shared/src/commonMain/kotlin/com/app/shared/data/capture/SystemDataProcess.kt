package com.app.shared.data.capture

expect class SystemDataProcess: DataProcess {
    override suspend fun process(capture: DataCapture.Item): DataProcess.Item
}