package com.app.feature.savecontent

import com.app.shared.business.Bookmark

class SaveContentViewState(content: Bookmark) {

    val resource = when(content) {
        is Bookmark.Text -> content.value
        is Bookmark.Link -> content.url
    }
}