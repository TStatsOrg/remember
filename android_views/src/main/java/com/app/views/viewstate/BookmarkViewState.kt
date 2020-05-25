package com.app.views.viewstate

import android.net.Uri
import android.view.View
import com.app.shared.business.BookmarkState
import java.text.SimpleDateFormat
import java.util.*

sealed class BookmarkViewState(private val bookmark: BookmarkState) {

    val id: Int = bookmark.id

    val topic: String = bookmark.topic?.name ?: ""

    abstract val source: String?

    val date: String
        get() {
            val format = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
            val jvmDat = Date(bookmark.date)
            return format.format(jvmDat)
        }

    val info: String
        get() {
            return "$source - $date"
        }

    data class Image(private val bookmark: BookmarkState.Image): BookmarkViewState(bookmark) {
        val url: Uri? = try { Uri.parse(bookmark.url) } catch (e: Throwable) { null }
        override val source: String? = "Image"
    }

    data class Text(private val bookmark: BookmarkState.Text): BookmarkViewState(bookmark) {
        val text: String = bookmark.text.capitalize()
        override val source: String? = "Clipped text"
    }

    data class Link(private val bookmark: BookmarkState.Link): BookmarkViewState(bookmark) {
        val title: String? = bookmark.title

        val destinationUrl: Uri?
            get() {
                return try {
                    Uri.parse(bookmark.url)
                } catch (e: Throwable) {
                    null
                }
            }

        val icon: Uri?
            get() {
                return try {
                    Uri.parse(bookmark.icon)
                } catch (e: Throwable) {
                    null
                }
            }

        val iconVisibility: Int
            get() {
                return if (icon == null) View.GONE else View.VISIBLE
            }

        override val source: String?
            get() {
                val url = try {
                    Uri.parse(bookmark.url)
                } catch (e: Throwable) {
                    null
                }

                return url?.host?.replaceProtocol()
            }

        private fun String.replaceProtocol(): String {
            return this.replace("http://", "").replace("https://", "")
        }
    }
}