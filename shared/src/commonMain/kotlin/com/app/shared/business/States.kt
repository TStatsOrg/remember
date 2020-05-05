package com.app.shared.business

import com.app.shared.redux.State

interface BookmarkState: State {

    val id: Int
    val date: Long
    val topic: TopicState?

    data class Text(
        override val id: Int,
        override val date: Long,
        override val topic: TopicState?,
        val text: String
    ) : BookmarkState

    data class Link(
        override val id: Int,
        override val date: Long,
        override val topic: TopicState?,
        val url: String,
        val title: String?,
        val caption: String?,
        val icon: String?
    ): BookmarkState

    data class Image(
        override val id: Int,
        override val date: Long,
        override val topic: TopicState?,
        val url: String
    ): BookmarkState

    data class Unsupported(
        override val id: Int,
        override val date: Long,
        override val topic: TopicState?
    ): BookmarkState
}

data class BookmarksState(
    val bookmarks: List<BookmarkState> = listOf(),
    val suggestions: List<TopicState> = listOf(),
    val date: Long = 0L,
    val error: Throwable? = null
): State

data class TopicState(
    val id: Int,
    val name: String
): State

data class TopicsState(
    val date: Long = 0L,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val topics: List<TopicState> = listOf()
)

data class EditBookmarkState(
    val bookmark: BookmarkState,
    val topics: List<TopicState> = listOf()
)

data class AppState(
    val bookmarks: BookmarksState = BookmarksState(),
    val topics: TopicsState = TopicsState(),
    val preview: BookmarkState? = null,
    val editBookmark: EditBookmarkState? = null
): State

/**
 * BookmarkState { Text, Image, Link, Unknown }
 * BookmarksState { bookmarks, data, error }
 *
 * TopicState { id, name }
 * TopicsState { (all)topics, date, isLoading, error }
 *
 * PreviewBookmark { bookmark }
 * EditBookmark { bookmark, (all)topics }
 */