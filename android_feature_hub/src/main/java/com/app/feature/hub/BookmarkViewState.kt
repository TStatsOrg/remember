package com.app.feature.hub

import com.app.shared.business.Bookmark

class BookmarkViewState(bookmark: Bookmark) {

    val id: Int = bookmark.id

    val content: String = when(bookmark) {
        is Bookmark.Text -> bookmark.value
        is Bookmark.Link -> bookmark.url
    }

    val type: String = when(bookmark) {
        is Bookmark.Text -> "T"
        is Bookmark.Link -> "L"
    }
}