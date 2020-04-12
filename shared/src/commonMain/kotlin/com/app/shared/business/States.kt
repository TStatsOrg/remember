package com.app.shared.business

import com.app.shared.redux.State

sealed class Bookmark: State {
    data class Text(val value: String): Bookmark()
    data class Link(val url: String): Bookmark()
}

data class AppState(
    val bookmarks: List<Bookmark> = listOf(),
    val preview: Bookmark? = null
): State