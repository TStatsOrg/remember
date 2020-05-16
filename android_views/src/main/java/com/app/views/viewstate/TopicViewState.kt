package com.app.views.viewstate

import android.view.View
import com.app.shared.business.TopicState

sealed class TopicViewState(state: TopicState) {
    val id: Int = state.id
    val name: String = state.name
    val topicState: TopicState = state

    data class Normal(val state: TopicState): TopicViewState(state)

    data class Selectable(val state: TopicState, val isSelected: Boolean): TopicViewState(state) {
        val checkMarkVisibility = if (isSelected) View.VISIBLE else View.INVISIBLE
    }
}