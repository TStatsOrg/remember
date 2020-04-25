package com.app.feature.topics

import com.app.shared.business.TopicsState

class TopicsViewState(state: TopicsState) {

    val topics: List<TopicViewState> = state.topics.map { TopicViewState(state = it) }
}