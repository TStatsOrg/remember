package com.app.feature.hub

import com.app.shared.business.BookmarkState

class BookmarkViewState(bookmark: BookmarkState) {

    val id: Int = bookmark.id

    val content: String = when(bookmark) {
        is BookmarkState.Text -> bookmark.value
        is BookmarkState.Link -> bookmark.url
    }

    val type: String = when(bookmark) {
        is BookmarkState.Text -> "T"
        is BookmarkState.Link -> "L"
    }
}