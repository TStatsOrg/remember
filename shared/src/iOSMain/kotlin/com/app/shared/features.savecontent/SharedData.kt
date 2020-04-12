package com.app.shared.features.savecontent

actual class SharedData {
    actual fun unbox(): SharedDataType {
        return SharedDataType.Text(content = "")
    }
}