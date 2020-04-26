package com.app.feature.topics

import com.app.shared.business.TopicsState

class TopicsViewState(state: TopicsState) {

    private val isEditing = state.mode is TopicsState.Mode.Editing

    val topics: List<TopicViewState> = state.topics.map { TopicViewState(state = it, isEditing = isEditing) }
}