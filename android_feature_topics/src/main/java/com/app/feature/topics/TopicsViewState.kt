package com.app.feature.topics

import com.app.shared.business.TopicsState
import com.app.views.viewstate.TopicViewState

data class TopicsViewState(val state: TopicsState) {
    val topics: List<TopicViewState> = state.topics.map { TopicViewState.Normal(state = it) }
}