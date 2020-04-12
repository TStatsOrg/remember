package com.app.shared.data.capture

import com.app.shared.coroutines.DefaultDispatcher
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

actual class SystemDataProcess: DataProcess {
    actual override suspend fun process(capture: DataCapture.Item): DataProcess.Item {
        return when (capture) {
            is DataCapture.Item.Text -> handleText(capture = capture)
            is DataCapture.Item.Image -> DataProcess.Item.Image(url = capture.url)
            DataCapture.Item.Unknown -> DataProcess.Item.Unknown
        }
    }

    private suspend fun handleText(capture: DataCapture.Item.Text): DataProcess.Item {
        if (capture.text.startsWith("http://") || capture.text.startsWith("https://")) {
            return handleLink(capture = capture)
        }

        return DataProcess.Item.Text(text = capture.text)
    }

    private suspend fun handleLink(capture: DataCapture.Item.Text): DataProcess.Item {
        return withContext(context = DefaultDispatcher) {

            // url
            val url = capture.text

            // get document
            val document = Jsoup.connect(url).get()
            // get title
            val title = document.title()

            // get description
            val descriptionElement = document.head().select("meta[name=description]").first()
            val description = descriptionElement?.attr("content")

            // first image
            val firstImageCandidate = document.head().select("link[href~=.*\\.(ico|png)]").first()
            val firstImageSrc = firstImageCandidate?.attr("href")
            // second image
            val secondImageCandidate = document.head().select("meta[itemprop=image]").first()
            val secondImageSrc = secondImageCandidate?.attr("itemprop")

            return@withContext DataProcess.Item.Link(
                url = url,
                title = title,
                description = description,
                icon = firstImageSrc ?: secondImageSrc)
        }
    }
}