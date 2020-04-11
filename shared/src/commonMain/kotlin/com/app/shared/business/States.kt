package com.app.shared.business

import com.app.shared.redux.State

sealed class SavedContent: State {
    data class Text(val value: String): SavedContent()
    data class Link(val url: String): SavedContent()
}

data class AppState(
    val allSavedContent: List<SavedContent> = listOf(),
    val previewContent: SavedContent? = null
): State