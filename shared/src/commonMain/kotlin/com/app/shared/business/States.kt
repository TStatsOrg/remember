package com.app.shared.business

import com.app.shared.redux.State

interface BookmarkState: State {

    val id: Int
    val date: Long

    data class Text(
        override val id: Int,
        override val date: Long,
        val text: String
    ) : BookmarkState

    data class Link(
        override val id: Int,
        override val date: Long,
        val url: String,
        val title: String?,
        val description: String?,
        val icon: String?
    ): BookmarkState

    data class Image(
        override val id: Int,
        override val date: Long,
        val url: String
    ): BookmarkState

    data class Unsupported(
        override val id: Int,
        override val date: Long
    ): BookmarkState
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