package com.app.feature.bookmark.edit

import com.app.shared.business.EditBookmarkState

data class EditBookmarkViewState(val state: EditBookmarkState) {

    val bookmarkId = state.bookmark.id

    val topicsViewState: List<SelectTopicViewState> = state.topics.map {
        SelectTopicViewState(state = it, isSelected = it.id == state.bookmark.topic?.id)
    }
}