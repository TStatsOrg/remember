package com.app.shared.data.capture

import org.jsoup.Jsoup

class AndroidDataProcess: RawDataProcess {

    override fun process(capture: String?): RawDataProcess.Item {
        // if null, it's unknown
        if (capture == null) {
            return RawDataProcess.Item.Unknown
        }

        // if not url, must be text
        if (!capture.isUrl()) {
            return RawDataProcess.Item.Text(text = capture)
        }

        // if it's image, leave it at that
        if (capture.isImage()) {
            return RawDataProcess.Item.Image(url = capture)
        }

        // else, is a link, so get details
        return try {
            val document = Jsoup.connect(capture).get()
            val title = document.title()
            val description = document.head().select("meta[name=description]").first()?.attr("content")
            val firstImageSrc = document.head().select("link[href~=.*\\.(ico|png)]").first()?.attr("href")
            val secondImageSrc = document.head().select("meta[itemprop=image]").first()?.attr("itemprop")

            RawDataProcess.Item.Link(
                url = capture,
                title = title,
                description = description,
                icon = firstImageSrc ?: secondImageSrc)
        } catch (e: Throwable) {
            RawDataProcess.Item.Unknown
        }
    }

    private fun String.isUrl(): Boolean {
        return this.startsWith("http://") || this.startsWith("https://") || this.startsWith("content://")
    }

    private fun String.isImage(): Boolean {
        return this.endsWith(".png") || this.endsWith(".jpeg") || this.endsWith(".jpg")
    }
}