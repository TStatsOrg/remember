package com.app.dependencies.data.dao

import com.app.shared.data.dao.FeedBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

class RealmFeedBookmarkDAO(override val instance: RealmProvider) : RealmDAO, FeedBookmarkDAO {
    override fun insert(dto: BookmarkDTO.FeedBookmarkDTO) = Unit

    override fun getAll(): List<BookmarkDTO.FeedBookmarkDTO> = listOf()

    override fun delete(bookmarkId: Int) = Unit

    override fun replaceTopic(topicId: Int, withTopicDTO: TopicDTO) = Unit
}