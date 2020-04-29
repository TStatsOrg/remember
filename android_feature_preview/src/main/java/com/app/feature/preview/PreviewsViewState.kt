package com.app.feature.preview

import com.app.shared.business.BookmarkState

data class PreviewsViewState(val state: BookmarkState) {

    val viewStates: List<PreviewViewState>
        get() {
            val viewState = when (state) {
                is BookmarkState.Link -> PreviewViewState.Link(bookmark = state)
                is BookmarkState.Text -> PreviewViewState.Text(bookmark = state)
                is BookmarkState.Image -> PreviewViewState.Image(bookmark = state)
                else -> null
            }

            return listOfNotNull(viewState)
        }
}