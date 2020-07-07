package com.app.shared.business

import com.app.shared.redux.AppState

interface BookmarkState: AppState {

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

    data class RSSFeed(
        override val id: Int,
        override val date: Long,
        override val topic: TopicState?,
        val url: String,
        val title: String?,
        val caption: String?,
        val icon: String?,
        val isSubscribed: Boolean
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
    val filterByTopic: TopicState? = null,
    val searchTerm: String? = null,
    val date: Long = 0L,
    val error: Throwable? = null
): AppState

data class TopicState(
    val id: Int,
    val name: String,
    val isEditable: Boolean
): AppState

data class TopicsState(
    val date: Long = 0L,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val topics: List<TopicState> = listOf()
): AppState

data class EditBookmarkState(
    val bookmark: BookmarkState,
    val topics: List<TopicState> = listOf()
): AppState

data class EditTopicState(
    val topic: TopicState
): AppState

data class PreviewState(
    val preview: BookmarkState? = null,
    val isLoading: Boolean = false
): AppState

data class FeedsState(
    val feeds: List<BookmarkState> = listOf(),
    val error: Throwable? = null
): AppState

data class RSSFeedItemState(
    val id: Int = 0,
    val title: String = "",
    val link: String = "",
    val pubDate: Long = 0L,
    val caption: String?
): AppState

data class RSSFeedDetailState(
    val feedState: BookmarkState? = null,
    val items: List<RSSFeedItemState> = listOf(),
    val error: Throwable? = null
): AppState {

    // iOS constructor
    constructor(): this(feedState = null, items = listOf(), error = null)
}

data class DisplayState(
    val item: BookmarkState.Link? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false,
    val isBookmarked: Boolean = false
): AppState {

    // iOS constructor
    constructor(): this(item = null, error = null, isLoading = false, isBookmarked = false)
}

data class MainState(
    val allBookmarks: List<BookmarkState> = listOf(),
    val bookmarks: BookmarksState = BookmarksState(),
    val topics: TopicsState = TopicsState(),
    val preview: PreviewState = PreviewState(),
    val editBookmark: EditBookmarkState? = null,
    val editTopic: EditTopicState? = null,
    val display: DisplayState = DisplayState(),
    val feedsState: FeedsState = FeedsState(),

    // deprecated
    val rssFeedDetail: RSSFeedDetailState = RSSFeedDetailState()
): AppState

@Deprecated(message = "Old state")
data class RSSFeedState(
    val id: Int = 0,
    val title: String = "",
    val description: String? = null,
    val link: String = "",
    val icon: String? = null,
    val isSubscribed: Boolean = false
): AppState
