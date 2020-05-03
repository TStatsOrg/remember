package com.app.feature.hub.viewstates

import com.app.shared.business.TopicsState

class TopicSuggestionsViewState(private val state: TopicsState) {

    val suggestions: List<TopicSuggestionViewState> = state.topics.map {
        TopicSuggestionViewState(topicState = it)
    }
}