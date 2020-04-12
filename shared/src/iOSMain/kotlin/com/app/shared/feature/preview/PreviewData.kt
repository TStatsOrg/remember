package com.app.shared.feature.preview

actual class PreviewData {
    actual fun unbox(): PreviewDataType {
        return PreviewDataType.Text(content = "")
    }
}