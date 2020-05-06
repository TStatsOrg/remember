package com.app.feature.hub.viewstates

import com.app.shared.business.BookmarkState
import com.app.shared.business.BookmarksState
import com.app.shared.business.TopicState
import com.app.views.viewstate.BookmarkViewState
import com.app.views.viewstate.TopicViewState

data class BookmarksViewState(val state: BookmarksState? = null) {

    private val bookmarks: List<BookmarkState> = state?.bookmarks ?: listOf()

    private val suggestions: List<TopicState> = state?.suggestions ?: listOf()

    val bookmarksViewState: List<BookmarkViewState> = bookmarks.mapNotNull {
        when (it) {
            is BookmarkState.Link -> BookmarkViewState.Link(bookmark = it)
            is BookmarkState.Text -> BookmarkViewState.Text(bookmark = it)
            is BookmarkState.Image -> BookmarkViewState.Image(bookmark = it)
            else -> null
        }
    }

    val suggestionsViewState: List<TopicViewState.Normal> = suggestions.map {
        TopicViewState.Normal(state = it)
    }
}