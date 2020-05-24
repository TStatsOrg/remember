package com.app.feature.hub.viewstates

import android.view.View
import com.app.shared.business.BookmarkState
import com.app.shared.business.BookmarksState
import com.app.views.viewstate.BookmarkViewState

data class BookmarksViewState(val state: BookmarksState? = null) {

    private val bookmarks: List<BookmarkState> = state?.bookmarks ?: listOf()

    val topicSearchText: String = state?.filterByTopic?.name?.let { "in: $it" } ?: ""

    val bookmarksViewState: List<BookmarkViewState> = bookmarks.mapNotNull {
        when (it) {
            is BookmarkState.Link -> BookmarkViewState.Link(bookmark = it)
            is BookmarkState.Text -> BookmarkViewState.Text(bookmark = it)
            is BookmarkState.Image -> BookmarkViewState.Image(bookmark = it)
            else -> null
        }
    }

    private val searchTextWhenFilteringByTopic: String
        get() {
            return state?.filterByTopic?.name ?: ""
        }

    private val currentSearchText: String
        get() {
            return state?.searchTerm ?: searchTextWhenFilteringByTopic
        }

    private val noSearchResults = state?.bookmarks?.isEmpty() ?: true

    val isFilteringByTopic: Boolean
        get() {
            return state?.filterByTopic != null
        }

    val noResultsVisibility = if (currentSearchText.isNotEmpty() && noSearchResults) View.VISIBLE else View.GONE

    val getStartedVisibility = if (currentSearchText.isEmpty() && noSearchResults) View.VISIBLE else View.GONE
}