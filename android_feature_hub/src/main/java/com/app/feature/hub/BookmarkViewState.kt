package com.app.feature.hub

import com.app.shared.business.BookmarkState

class BookmarkViewState(bookmark: BookmarkState) {

    val id: Int = bookmark.id

    val content: String? = when(bookmark) {
        is BookmarkState.Text -> bookmark.text
        is BookmarkState.Link -> bookmark.title
        is BookmarkState.Image -> bookmark.url
        is BookmarkState.Unsupported -> "${bookmark.id}"
        else -> null
    }

    val type: String = when(bookmark) {
        is BookmarkState.Text -> "T"
        is BookmarkState.Link -> "L"
        is BookmarkState.Image -> "I"
        is BookmarkState.Unsupported -> "N/A"
        else -> "N/A"
    }
}