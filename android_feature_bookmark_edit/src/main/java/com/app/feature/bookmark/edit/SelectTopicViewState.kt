package com.app.feature.bookmark.edit

import android.view.View
import com.app.shared.business.TopicState

data class SelectTopicViewState(val state: TopicState, val isSelected: Boolean) {
    val id: Int = state.id
    val name: String = state.name
    val checkMarkVisibility = if (isSelected) View.VISIBLE else View.INVISIBLE
}