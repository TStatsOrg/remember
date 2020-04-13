package com.app.shared.data.capture

import org.jsoup.Jsoup

actual class SystemDataProcess: DataProcess {

    actual override fun process(capture: DataCapture.Item): DataProcess.Item {
        return when (capture) {
            is DataCapture.Item.Text -> handleText(capture = capture)
            is DataCapture.Item.Image -> DataProcess.Item.Image(url = capture.url)
            else -> DataProcess.Item.Unknown
        }
    }

    private fun handleText(capture: DataCapture.Item.Text): DataProcess.Item {
        if (capture.text.startsWith("http://") || capture.text.startsWith("https://")) {
            return handleLink(capture = capture)
        }

        return DataProcess.Item.Text(text = capture.text)
    }

    private fun handleLink(capture: DataCapture.Item.Text): DataProcess.Item {
        // url
        val url = capture.text

        // get document
        return try {
            val document = Jsoup.connect(url).get()
            val title = document.title()
            val description = document.head().select("meta[name=description]").first()?.attr("content")
            val firstImageSrc = document.head().select("link[href~=.*\\.(ico|png)]").first()?.attr("href")
            val secondImageSrc = document.head().select("meta[itemprop=image]").first()?.attr("itemprop")

            DataProcess.Item.Link(
                url = url,
                title = title,
                description = description,
                icon = firstImageSrc ?: secondImageSrc)
        } catch (e: Throwable) {
            DataProcess.Item.Unknown
        }
    }
}