package com.app.shared.data.dao

import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

interface TextBookmarkDAO {
    fun insert(dto: BookmarkDTO.TextBookmarkDTO)
    fun getAll(): List<BookmarkDTO.TextBookmarkDTO>
    fun delete(bookmarkId: Int)
    fun replaceTopic(topicId: Int, withTopicDTO: TopicDTO)
}