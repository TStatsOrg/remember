package com.app.feature.hub.viewstates

import com.app.shared.business.BookmarkState

data class BookmarksViewState(val state: List<BookmarkState>) {

    val viewStates: List<BookmarkViewState> = state.mapNotNull {
        when (it) {
            is BookmarkState.Link -> BookmarkViewState.Link(
                bookmark = it
            )
            is BookmarkState.Text -> BookmarkViewState.Text(
                bookmark = it
            )
            is BookmarkState.Image -> BookmarkViewState.Image(
                bookmark = it
            )
            else -> null
        }
    }
}