package com.app.shared.data.dao

import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

interface ImageBookmarkDAO {
    fun insert(dto: BookmarkDTO.ImageBookmarkDTO)
    fun getAll(): List<BookmarkDTO.ImageBookmarkDTO>
    fun delete(bookmarkId: Int)
    fun replaceTopic(topicId: Int, withTopicDTO: TopicDTO)
}