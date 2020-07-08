package com.app.shared.data.dao

import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

interface FeedBookmarkDAO {
    fun insert(dto: BookmarkDTO.FeedBookmarkDTO)
    fun getAll(): List<BookmarkDTO.FeedBookmarkDTO>
    fun delete(bookmarkId: Int)
    fun replaceTopic(topicId: Int, withTopicDTO: TopicDTO)
}