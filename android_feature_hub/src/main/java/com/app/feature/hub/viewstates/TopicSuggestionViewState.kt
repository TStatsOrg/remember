package com.app.feature.hub.viewstates

import com.app.shared.business.TopicState

data class TopicSuggestionViewState(val topicState: TopicState) {
    val id: Int = topicState.id
    val name: String = topicState.name
}