package com.app.shared.data.dao

import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

interface FeedBookmarkDAO {
    fun insert(dto: BookmarkDTO.RSSFeedBookmarkDTO)
    fun getAll(): List<BookmarkDTO.RSSFeedBookmarkDTO>
    fun delete(bookmarkId: Int)
    fun replaceTopic(topicId: Int, withTopicDTO: TopicDTO)
}