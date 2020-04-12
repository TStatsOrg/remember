package com.app.shared.business

import com.app.shared.redux.Action

sealed class Actions: Action {

    sealed class PreviewContent: Actions() {
        object Reset: PreviewContent()
        data class Text(val content: String): PreviewContent()
        data class Link(val url: String): PreviewContent()
    }

    object SaveContent: Actions()
}