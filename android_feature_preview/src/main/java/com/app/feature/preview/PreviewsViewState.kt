package com.app.feature.preview

import com.app.shared.business.BookmarkState
import com.app.views.viewstate.BookmarkViewState

data class PreviewsViewState(val state: BookmarkState) {

    val viewStates: List<BookmarkViewState>
        get() {
            val viewState = when (state) {
                is BookmarkState.Link -> BookmarkViewState.Link(bookmark = state)
                is BookmarkState.Text -> BookmarkViewState.Text(bookmark = state)
                is BookmarkState.Image -> BookmarkViewState.Image(bookmark = state)
                else -> null
            }

            return listOfNotNull(viewState)
        }
}