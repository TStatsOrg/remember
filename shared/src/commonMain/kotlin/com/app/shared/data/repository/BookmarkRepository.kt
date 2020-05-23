package com.app.shared.data.repository

import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

interface BookmarkRepository {
    suspend fun save(dto: BookmarkDTO)
    suspend fun load(): List<BookmarkDTO>
    suspend fun delete(bookmarkId: Int)
    suspend fun replaceTopic(topicId: Int, withTopicDTO: TopicDTO)
}