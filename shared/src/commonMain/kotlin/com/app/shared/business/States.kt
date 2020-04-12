package com.app.shared.business

import com.app.shared.redux.State

sealed class Bookmark(open val id: Int): State {
    data class Text(override val id: Int, val value: String): Bookmark(id)
    data class Link(override val id: Int, val url: String): Bookmark(id)
}

data class Bookmarks(
    val bookmarks: List<Bookmark> = listOf(),
    val time: Long = 0L
): State

data class AppState(
    val bookmarks: Bookmarks = Bookmarks(),
    val preview: Bookmark? = null
): State