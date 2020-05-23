package com.app.shared.data.dao

import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

interface LinkBookmarkDAO {
    fun insert(dto: BookmarkDTO.LinkBookmarkDTO)
    fun getAll(): List<BookmarkDTO.LinkBookmarkDTO>
    fun delete(bookmarkId: Int)
    fun replaceTopic(topicId: Int, withTopicDTO: TopicDTO)
}