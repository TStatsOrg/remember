package com.app.shared.data.capture

import android.content.Intent
import android.net.Uri
import android.os.Parcelable

class IntentDataCapture(private val intent: Intent): RawDataCapture {

    override fun capture(data: (String?) -> Unit) {
        val action = intent.action
        val type = intent.type

        if (action != Intent.ACTION_SEND) {
            data(null)
            return
        }

        if (type == null) {
            data(null)
            return
        }

        if (type.startsWith("text/")) {
            val content = intent.content()
            data(content)
            return
        }

        if (type.startsWith("image/")) {
            val uri = intent.uri()
            data(uri)
            return
        }

        data(null)
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