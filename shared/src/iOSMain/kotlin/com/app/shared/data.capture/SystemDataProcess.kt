package com.app.shared.data.capture

actual class SystemDataProcess: DataProcess {
    actual override suspend fun process(capture: DataCapture.Item): DataProcess.Item {
        return DataProcess.Item.Unknown
    }
}