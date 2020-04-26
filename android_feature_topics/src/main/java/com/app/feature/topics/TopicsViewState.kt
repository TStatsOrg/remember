package com.app.feature.topics

import com.app.shared.business.TopicsState

data class TopicsViewState(val state: TopicsState) {
    val topics: List<TopicViewState> = state.topics.map { TopicViewState(state = it) }
}