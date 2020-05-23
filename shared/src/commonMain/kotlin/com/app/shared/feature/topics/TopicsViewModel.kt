package com.app.shared.feature.topics

import com.app.shared.business.TopicState
import com.app.shared.business.TopicsState

interface TopicsViewModel {
    fun loadTopics()
    fun filter(byTopic: TopicState)
    fun delete(topicId: Int)
    fun observeTopicState(callback: (TopicsState) -> Unit)
    fun cleanup()
}