package com.app.shared.business

import com.app.shared.redux.Action

sealed class Actions: Action {

    sealed class Bookmark: Actions() {

        sealed class Preview: Bookmark() {
            object Reset: Preview()
            data class Text(val content: String): Preview()
            data class Link(val url: String): Preview()
        }

        data class Load(val time: Long): Bookmark()

        data class Loaded(val time: Long, val bookmarks: List<com.app.shared.business.Bookmark>): Bookmark()
    }
}