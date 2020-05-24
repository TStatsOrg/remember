package com.app.feature.preview

import android.view.View
import com.app.shared.business.BookmarkState
import com.app.shared.business.PreviewState
import com.app.views.viewstate.BookmarkViewState

data class PreviewsViewState(val state: PreviewState) {

    private val isLoading = state.isLoading

    val spinnerVisibility = if (isLoading) View.VISIBLE else View.GONE

    val viewStates: List<BookmarkViewState>
        get() {
            val viewState = when (val preview = state.preview) {
                is BookmarkState.Link -> BookmarkViewState.Link(bookmark = preview)
                is BookmarkState.Text -> BookmarkViewState.Text(bookmark = preview)
                is BookmarkState.Image -> BookmarkViewState.Image(bookmark = preview)
                else -> null
            }

            return listOfNotNull(viewState)
        }
}