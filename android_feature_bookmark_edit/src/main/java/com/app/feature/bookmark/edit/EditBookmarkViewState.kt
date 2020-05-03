package com.app.feature.bookmark.edit

import com.app.shared.business.EditBookmarkState
import com.app.views.viewstate.TopicViewState

data class EditBookmarkViewState(val state: EditBookmarkState) {

    val bookmarkId = state.bookmark.id

    val topicsViewState: List<TopicViewState.Selectable> = state.topics.map {
        TopicViewState.Selectable(state = it, isSelected = it.id == state.bookmark.topic?.id)
    }
}