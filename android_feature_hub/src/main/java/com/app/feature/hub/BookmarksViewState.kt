package com.app.feature.hub

import com.app.shared.business.BookmarkState

class BookmarksViewState(state: List<BookmarkState>) {

    val title: String = "You have ${state.size} bookmarks"

    val viewStates: List<BookmarkViewState> = state.mapNotNull {
        when (it) {
            is BookmarkState.Link -> BookmarkViewState.Link(bookmark = it)
            is BookmarkState.Text -> BookmarkViewState.Text(bookmark = it)
            is BookmarkState.Image -> BookmarkViewState.Image(bookmark = it)
            else -> null
        }
    }
}