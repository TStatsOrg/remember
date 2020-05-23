package com.app.shared.data.dao

import com.app.shared.data.dto.TopicDTO

interface TopicDAO {
    fun insert(dto: TopicDTO)
    fun getAll(): List<TopicDTO>
    fun delete(topicId: Int)
}