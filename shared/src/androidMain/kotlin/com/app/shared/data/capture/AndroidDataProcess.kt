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
            val description = document.head().select("meta[name=description]").first()?.attr("content")
            val firstImageSrc = document.head().select("link[href~=.*\\.(ico|png)]").first()?.attr("href")
            val secondImageSrc = document.head().select("meta[itemprop=image]").first()?.attr("itemprop")
            val thirdImageSrc = document.getElementsByTag("img")?.first()?.attr("href")

            RawDataProcess.Item.Link(
                url = capture,
                title = title,
                description = description,
                icon = firstImageSrc ?: secondImageSrc ?: thirdImageSrc)
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