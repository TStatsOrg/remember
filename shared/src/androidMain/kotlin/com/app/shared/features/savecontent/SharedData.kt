package com.app.shared.features.savecontent

import android.content.Intent

actual class SharedData(private val intent: Intent) {

    private fun Intent.isSendAction(): Boolean {
        return this.action == Intent.ACTION_SEND
    }

    private fun Intent.isOfMimeType(type: HandledContent): Boolean {
        return this.type?.startsWith(type.mimeType) ?: false
    }

    private fun isUrl(string: String): Boolean {
        return string.startsWith("http://") || string.startsWith("https://")
    }

    actual fun unbox(): SharedDataType {
        if (!intent.isSendAction()) {
            return SharedDataType.Unsupported
        }

        if (intent.isOfMimeType(HandledContent.Text)) {
            val textValue: String? = intent.getStringExtra(Intent.EXTRA_TEXT)

            if (textValue != null) {
                return if (isUrl(string = textValue)) {
                    SharedDataType.Link(url = textValue)
                } else {
                    SharedDataType.Text(content = textValue)
                }
            }

            return SharedDataType.Unsupported
        }

        if (intent.isOfMimeType(HandledContent.Image)) {
            return SharedDataType.Unsupported
        }

        return SharedDataType.Unsupported
    }

    private enum class HandledContent(val mimeType: String) {
        Text(mimeType = "text/"),
        Image(mimeType = "image/")
    }
}