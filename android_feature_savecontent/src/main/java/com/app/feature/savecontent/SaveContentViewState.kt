package com.app.feature.savecontent

import com.app.shared.business.SavedContent

class SaveContentViewState(private val content: SavedContent) {

    val resource = when(content) {
        is SavedContent.Text -> content.value
        is SavedContent.Link -> content.url
    }
}