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

sealed class BookmarkState2(open val id: Int, open val date: Long): State {

    data class Text(
        override val id: Int,
        override val date: Long,
        val text: String
    ) : BookmarkState2(id, date)

    data class Link(
        override val id: Int,
        override val date: Long,
        val url: String,
        val title: String?,
        val description: String?,
        val icon: String?
    ): BookmarkState2(id, date)

    data class Image(
        override val id: Int,
        override val date: Long,
        val url: String
    ): BookmarkState2(id, date)

    data class Unsupported(
        override val id: Int,
        override val date: Long
    ): BookmarkState2(id, date)
}

data class SavedBookmarksState(
    val bookmarks: List<BookmarkState2> = listOf(),
    val date: Long = 0L,
    val error: Throwable? = null
): State

data class AppState2(
    val bookmarks: SavedBookmarksState = SavedBookmarksState(),
    val preview: BookmarkState2? = null
): State