package com.app.shared.data.capture

import android.content.Intent
import android.net.Uri
import android.os.Parcelable

class IntentDataCapture: RawDataCapture<Intent> {

    override fun capture(input: Intent, data: (String?) -> Unit) {
        val action = input.action
        val type = input.type

        if (action != Intent.ACTION_SEND) {
            data(null)
            return
        }

        if (type == null) {
            data(null)
            return
        }

        if (type.startsWith("text/")) {
            val content = input.content()
            data(content)
            return
        }

        if (type.startsWith("image/")) {
            val uri = input.uri()
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