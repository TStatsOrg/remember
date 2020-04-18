package com.app.feature.hub

import android.net.Uri
import com.app.shared.business.BookmarkState

sealed class BookmarkViewState(bookmark: BookmarkState) {

    val id: Int = bookmark.id
    val date: String = "20 Apr 2020"

    data class Image(private val bookmark: BookmarkState.Image): BookmarkViewState(bookmark) {
        val url: Uri? = try { Uri.parse(bookmark.url) } catch (e: Throwable) { null }
    }

    data class Text(private val bookmark: BookmarkState.Text): BookmarkViewState(bookmark) {
        val text: String = bookmark.text
    }

    data class Link(private val bookmark: BookmarkState.Link): BookmarkViewState(bookmark) {
        val title: String? = bookmark.title
        val caption: String? = bookmark.description
        val icon: Uri? = try { Uri.parse(bookmark.icon) } catch (e: Throwable) { null }
    }
}