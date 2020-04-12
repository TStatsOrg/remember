package com.app.shared.features.preview

actual class PreviewData {
    actual fun unbox(): PreviewDataType {
        return PreviewDataType.Text(content = "")
    }
}