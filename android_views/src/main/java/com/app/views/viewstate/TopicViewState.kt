package com.app.views.viewstate

import android.view.View
import com.app.shared.business.TopicState

sealed class TopicViewState(state: TopicState, noBookmarks: Int) {
    val id: Int = state.id
    val name: String = state.name
    val topicState: TopicState = state
    val isEditable: Boolean = state.isEditable
    val numberOfBookmarks = noBookmarks

    data class Normal(val state: TopicState, val noBookmarks: Int): TopicViewState(state, noBookmarks)

    data class Selectable(val state: TopicState, val isSelected: Boolean, val noBookmarks: Int): TopicViewState(state, noBookmarks) {
        val checkMarkVisibility = if (isSelected) View.VISIBLE else View.INVISIBLE
    }
}