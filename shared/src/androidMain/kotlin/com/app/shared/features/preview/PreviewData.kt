package com.app.shared.features.preview

import android.content.Intent

actual class PreviewData(private val intent: Intent) {

    private fun Intent.isSendAction(): Boolean {
        return this.action == Intent.ACTION_SEND
    }

    private fun Intent.isOfMimeType(type: HandledContent): Boolean {
        return this.type?.startsWith(type.mimeType) ?: false
    }

    private fun isUrl(string: String): Boolean {
        return string.startsWith("http://") || string.startsWith("https://")
    }

    actual fun unbox(): PreviewDataType {
        if (!intent.isSendAction()) {
            return PreviewDataType.Unsupported
        }

        if (intent.isOfMimeType(HandledContent.Text)) {
            val textValue: String? = intent.getStringExtra(Intent.EXTRA_TEXT)

            if (textValue != null) {
                return if (isUrl(string = textValue)) {
                    PreviewDataType.Link(url = textValue)
                } else {
                    PreviewDataType.Text(content = textValue)
                }
            }

            return PreviewDataType.Unsupported
        }

        if (intent.isOfMimeType(HandledContent.Image)) {
            return PreviewDataType.Unsupported
        }

        return PreviewDataType.Unsupported
    }

    private enum class HandledContent(val mimeType: String) {
        Text(mimeType = "text/"),
        Image(mimeType = "image/")
    }
}