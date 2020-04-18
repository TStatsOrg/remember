package com.app.shared.business

import com.app.shared.redux.State

sealed class BookmarkState(open val id: Int, open val date: Long): State {

    data class Text(
        override val id: Int,
        override val date: Long,
        val text: String
    ) : BookmarkState(id, date)

    data class Link(
        override val id: Int,
        override val date: Long,
        val url: String,
        val title: String?,
        val description: String?,
        val icon: String?
    ): BookmarkState(id, date)

    data class Image(
        override val id: Int,
        override val date: Long,
        val url: String
    ): BookmarkState(id, date)

    data class Unsupported(
        override val id: Int,
        override val date: Long
    ): BookmarkState(id, date)
}

data class SavedBookmarksState(
    val bookmarks: List<BookmarkState> = listOf(),
    val date: Long = 0L,
    val error: Throwable? = null
): State

data class AppState(
    val bookmarks: SavedBookmarksState = SavedBookmarksState(),
    val preview: BookmarkState? = null
): State