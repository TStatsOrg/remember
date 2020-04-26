package com.app.shared.feature.topics

import com.app.shared.business.TopicsState

interface TopicsViewModel {
    fun loadTopics(forBookmarkId: Int? = null)

//    fun update(bookmark: Int, withTopic: Int)

    fun observeTopicState(callback: (TopicsState) -> Unit)

//    fun observeBookmarkUpdated(callback: () -> Unit)
}