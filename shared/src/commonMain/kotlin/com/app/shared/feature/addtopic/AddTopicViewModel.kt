package com.app.shared.feature.addtopic

interface AddTopicViewModel {
    fun addTopic(name: String)
    fun observeTopicsSaved(callback: () -> Unit)
}