package com.app.feature.hub.viewstates

import com.app.shared.business.TopicsState
import com.app.views.viewstate.TopicViewState

data class SuggestionsViewState(val state: TopicsState) {

    val suggestions: List<TopicViewState> = state.topics.map {
        TopicViewState.Normal(state = it)
    }
}