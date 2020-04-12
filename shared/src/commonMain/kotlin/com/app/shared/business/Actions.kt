package com.app.shared.business

import com.app.shared.redux.Action

sealed class Actions: Action {

    sealed class Preview: Actions() {
        object Reset: Preview()
        data class Text(val content: String): Preview()
        data class Link(val url: String): Preview()
    }

    object SaveBookmark: Actions()
}