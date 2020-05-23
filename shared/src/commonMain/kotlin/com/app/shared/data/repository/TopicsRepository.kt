package com.app.shared.data.repository

import com.app.shared.data.dto.TopicDTO

interface TopicsRepository {
    suspend fun save(dto: TopicDTO)
    suspend fun load(): List<TopicDTO>
    suspend fun delete(topicId: Int)
}