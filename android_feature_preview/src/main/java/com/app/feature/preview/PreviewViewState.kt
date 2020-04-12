package com.app.feature.preview

import com.app.shared.business.BookmarkState

class PreviewViewState(content: BookmarkState) {

    val resource = when(content) {
        is BookmarkState.Text -> content.value
        is BookmarkState.Link -> content.url
    }
}