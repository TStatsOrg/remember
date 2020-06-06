package com.app.dependencies.data.capture

import com.app.shared.coroutines.DispatcherFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class HTMLParser {

    fun parse(url: String, result: (Result<Output>) -> Unit) {
        GlobalScope.launch(context = DispatcherFactory.main()) {

            val output = withContext(context = DispatcherFactory.default()) {
                return@withContext parse(url)
            }

            when {
                output != null -> result(Result.success(value = output))
                else -> result(Result.failure(exception = Throwable()))
            }
        }
    }

    private fun parse(url: String): Output? {
        try {
            val document = Jsoup.connect(url).get()
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

            return Output(
                title = title,
                description = description,
                icon = thumbnail2Src ?: thumbnail1Src ?: iconImageSrc
            )
        } catch (e: Throwable) {
            return null
        }
    }

    data class Output(
        val title: String?,
        val description: String?,
        val icon: String?
    )
}