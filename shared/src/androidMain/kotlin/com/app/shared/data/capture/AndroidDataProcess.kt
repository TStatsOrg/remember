package com.app.shared.data.capture

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import org.jsoup.Jsoup

class AndroidDataProcess(private val context: Context): RawDataProcess {

    override fun process(capture: String?): RawDataProcess.Item {
        // if null, it's unknown
        if (capture == null) {
            return RawDataProcess.Item.Unknown
        }

        // if not url, must be text
        if (!capture.isUrl()) {
            return RawDataProcess.Item.Text(text = capture)
        }

        // if it's image
        if (capture.isImage()) {
            // it's an image from the network
            if (capture.isUrlImage()) {
                return RawDataProcess.Item.Image(url = capture)
            }

            // it's a new content type image that must be read from disk
            if (capture.isContentImage()) {
                try {
                    val uri = Uri.parse(capture)
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

                    return RawDataProcess.Item.Image(url = outputUrl!!.toString())
                } catch (e: Throwable) {
                    return RawDataProcess.Item.Unknown
                }
            }

            // not a known image type
            return RawDataProcess.Item.Unknown
        }

        // else, is a link, so get details
        return try {
            val document = Jsoup.connect(capture).get()
            val title = document.title()
            val description = try {
                document.head().select("meta[name=description]").first()?.attr("content")
            } catch (e: Throwable) {
                null
            }
            val iconImageSrc = try {
                document.head().select("link[href~=.*\\.(ico|png)]").first()?.attr("href")
            } catch (e: Throwable) {
                null
            }
            val thumbnail1Src = try {
                document.body().select("link[rel=\"image_src\"]").first()?.attr("href")
            } catch (e: Throwable) {
                null
            }
            val thumbnail2Src = try {
                document.head().select("meta[property=og:image]").first()?.attr("content")
            } catch (e: Throwable) {
                null
            }

            RawDataProcess.Item.Link(
                url = capture,
                title = title,
                description = description,
                icon = thumbnail2Src ?: thumbnail1Src ?: iconImageSrc)
        } catch (e: Throwable) {
            RawDataProcess.Item.Unknown
        }
    }

    private fun String.isUrl(): Boolean {
        return this.startsWith("http://") || this.startsWith("https://") || this.startsWith("content://")
    }

    private fun String.isUrlImage(): Boolean {
        return this.startsWith("http://") || this.startsWith("https://")
    }

    private fun String.isContentImage(): Boolean {
        return this.startsWith("content://")
    }

    private fun String.isImage(): Boolean {
        return this.endsWith(".png") || this.endsWith(".jpeg") || this.endsWith(".jpg")
    }
}