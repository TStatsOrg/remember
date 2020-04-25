package com.app.feature.hub

import android.net.Uri
import com.app.shared.business.BookmarkState
import java.text.SimpleDateFormat
import java.util.*

sealed class BookmarkViewState(private val bookmark: BookmarkState) {

    val id: Int = bookmark.id

    val date: String
        get() {
            val format = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val jvmDat = Date(bookmark.date)
            return format.format(jvmDat)
        }

    data class Image(private val bookmark: BookmarkState.Image): BookmarkViewState(bookmark) {
        val url: Uri? = try { Uri.parse(bookmark.url) } catch (e: Throwable) { null }
        val source: String = "Image"
        val topic: String = "All topics"
    }

    data class Text(private val bookmark: BookmarkState.Text): BookmarkViewState(bookmark) {
        val text: String = bookmark.text
        val source: String = "Clipped text"
        val topic: String = "All topics"
    }

    data class Link(private val bookmark: BookmarkState.Link): BookmarkViewState(bookmark) {
        val title: String? = bookmark.title

        val source: String?
            get() {
                val url = try {
                    Uri.parse(bookmark.url)
                } catch (e: Throwable) {
                    null
                }

                return url?.host?.replaceProtocol()
            }

        val topic: String = "All topics"

        private fun String.replaceProtocol(): String {
            return this.replace("http://", "").replace("https://", "")
        }
    }
}