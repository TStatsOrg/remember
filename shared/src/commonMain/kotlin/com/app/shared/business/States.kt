package com.app.shared.business

import com.app.shared.redux.State

sealed class BookmarkState(open val id: Int): State {
    data class Text(override val id: Int, val value: String): BookmarkState(id)
    data class Link(override val id: Int, val url: String): BookmarkState(id)
}

data class BookmarksState(
    val bookmarks: List<BookmarkState> = listOf(),
    val time: Long = 0L
): State

data class AppState(
    val bookmarks: BookmarksState = BookmarksState(),
    val preview: BookmarkState? = null
): State