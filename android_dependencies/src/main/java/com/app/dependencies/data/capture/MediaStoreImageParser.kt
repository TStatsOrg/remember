package com.app.dependencies.data.capture

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore

class MediaStoreImageParser(private val context: Context): ImageParser {

    override fun parse(url: String): String? {

        // it's an image from the network
        if (url.isUrlImage()) {
            return url
        }

        if (url.isContentImage()) {
            try {
                val uri = Uri.parse(url)
                val resolver = context.contentResolver
                val inputStream = resolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)

                val values = ContentValues(1)
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

                val outputUrl = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

                val outputStream = resolver.openOutputStream(outputUrl!!)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)

                inputStream?.close()
                outputStream?.close()

                return outputUrl.toString()
            } catch (e: Throwable) {
                return null
            }
        }

        return null
    }

    private fun String.isUrlImage(): Boolean {
        return this.startsWith("http://") || this.startsWith("https://")
    }

    private fun String.isContentImage(): Boolean {
        return this.startsWith("content://")
    }
}