package com.app.feature.topics

import com.app.shared.business.BookmarkState
import com.app.shared.business.TopicState
import com.app.shared.business.TopicsState
import com.app.views.viewstate.TopicViewState

data class TopicsViewState(
    val state: TopicsState,
    val bookmarks: List<BookmarkState>
) {
    val topics: List<TopicViewState> = state.topics.map {
        TopicViewState.Normal(state = it, noBookmarks = noBookmarks(fromTopic = it))
    }

    private fun noBookmarks(fromTopic: TopicState): Int {
        return bookmarks.filter { it.topic?.id == fromTopic.id }.size
    }
}