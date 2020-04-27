package com.app.feature.preview

import com.app.shared.business.BookmarkState

data class PreviewsViewState(val state: List<BookmarkState>) {

    val viewStates: List<PreviewViewState> = state.mapNotNull {
        when (it) {
            is BookmarkState.Link -> PreviewViewState.Link(bookmark = it)
            is BookmarkState.Text -> PreviewViewState.Text(bookmark = it)
            is BookmarkState.Image -> PreviewViewState.Image(bookmark = it)
            else -> null
        }
    }
}