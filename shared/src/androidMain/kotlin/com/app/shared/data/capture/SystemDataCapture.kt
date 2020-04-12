package com.app.shared.data.capture

import android.content.Intent
import android.net.Uri
import android.os.Parcelable

actual class SystemDataCapture(private val intent: Intent): DataCapture {

    actual override fun unbox(): DataCapture.Item {
        val action = intent.action
        val type = intent.type

        if (action != Intent.ACTION_SEND) {
            return DataCapture.Item.Unknown
        }

        if (type == null) {
            return DataCapture.Item.Unknown
        }

        if (type.startsWith("text/")) {
            val content = intent.content()
            if (content != null) {
                return DataCapture.Item.Text(text = content)
            }
        }

        if (type.startsWith("image/")) {
            val uri = intent.uri()
            if (uri != null) {
                return DataCapture.Item.Image(url = uri)
            }
        }

        return DataCapture.Item.Unknown
    }

    private fun Intent.content(): String? {
        return try {
            this.getStringExtra(Intent.EXTRA_TEXT)
        } catch (e: Throwable) {
            null
        }
    }

    private fun Intent.uri(): String? {
        return try {
            (this.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.toString()
        } catch (e: Throwable) {
            null
        }
    }
}