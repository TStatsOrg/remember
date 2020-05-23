package com.app.shared.feature.edittopic

import com.app.shared.business.EditTopicState

interface EditTopicViewModel {
    fun loadEditableTopic(forTopicId: Int)
    fun updateTopic(topicId: Int, name: String)
    fun cleanup()

    fun observeEditTopicState(callback: (EditTopicState) -> Unit)
    fun observeTopicUpdated(callback: (Boolean) -> Unit)
}