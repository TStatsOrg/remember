package com.app.feature.topics

import com.app.shared.business.TopicState

data class TopicViewState(val state: TopicState) {

    val id: Int = state.id
    val name: String = state.name
}