package com.app.feature.topics

import android.view.View
import com.app.shared.business.TopicState

class TopicViewState(state: TopicState, val isEditing: Boolean) {

    val id: Int = state.id
    val name: String = state.name

    val checkMarkVisibility = when (isEditing) {
        true -> if (state.isSelected) View.VISIBLE else View.INVISIBLE
        else -> View.GONE
    }
}