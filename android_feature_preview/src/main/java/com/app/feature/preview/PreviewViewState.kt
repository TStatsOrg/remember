package com.app.feature.preview

import com.app.shared.business.BookmarkState

class PreviewViewState(content: BookmarkState) {

    val resource = when(content) {
        is BookmarkState.Text -> content.text
        is BookmarkState.Link -> content.title
        is BookmarkState.Image -> content.url
        is BookmarkState.Unsupported -> content.id
        else -> null
    }
}