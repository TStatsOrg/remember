package com.app.shared.feature.addtopic

interface AddTopicViewModel {
    fun addTopic(name: String)
    fun observeTopicSaved(callback: () -> Unit)
}