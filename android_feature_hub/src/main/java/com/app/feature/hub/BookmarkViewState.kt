package com.app.feature.hub

import android.net.Uri
import android.view.View
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
    }

    data class Text(private val bookmark: BookmarkState.Text): BookmarkViewState(bookmark) {
        val text: String = bookmark.text
    }

    data class Link(private val bookmark: BookmarkState.Link): BookmarkViewState(bookmark) {
        val title: String? = bookmark.title

        val caption: String? = bookmark.caption

        val icon: Uri? = bookmark.icon?.let {
            try {
                Uri.parse(it)
            } catch (e: Throwable) {
                null
            }
        }

        val iconVisibility: Int
            get() {
                val isNotNull = icon != null
                val isNotEmpty = icon?.toString()?.isNotEmpty() ?: false
                return if (isNotNull && isNotEmpty) View.VISIBLE else View.GONE
            }

        val captionVisibility: Int
            get() {
                val isNotNull = caption != null
                val isNotEmpty = caption?.isNotEmpty() ?: false
                return if (isNotNull && isNotEmpty) View.VISIBLE else View.GONE
            }
    }
}