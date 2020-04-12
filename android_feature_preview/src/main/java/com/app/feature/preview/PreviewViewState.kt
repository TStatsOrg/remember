package com.app.feature.preview

import com.app.shared.business.Bookmark

class PreviewViewState(content: Bookmark) {

    val resource = when(content) {
        is Bookmark.Text -> content.value
        is Bookmark.Link -> content.url
    }
}