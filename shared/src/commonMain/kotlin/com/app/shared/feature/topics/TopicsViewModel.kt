package com.app.shared.feature.topics

import com.app.shared.business.TopicsState

interface TopicsViewModel {
    fun loadTopics()
    fun observeTopicState(callback: (TopicsState) -> Unit)
}