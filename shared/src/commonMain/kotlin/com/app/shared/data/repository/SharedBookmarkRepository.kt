package com.app.shared.data.repository

import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.data.dao.ImageBookmarkDAO
import com.app.shared.data.dao.LinkBookmarkDAO
import com.app.shared.data.dao.RSSFeedBookmarkDAO
import com.app.shared.data.dao.TextBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO
import kotlinx.coroutines.withContext

class SharedBookmarkRepository(
    private val imageBookmarkDAO: ImageBookmarkDAO,
    private val textBookmarkDAO: TextBookmarkDAO,
    private val linkBookmarkDAO: LinkBookmarkDAO,
    private val rssFeedBookmarkDAO: RSSFeedBookmarkDAO
): BookmarkRepository {

    override suspend fun save(dto: BookmarkDTO) {
        withContext(context = DispatcherFactory.io()) {
            when (dto) {
                is BookmarkDTO.TextBookmarkDTO -> textBookmarkDAO.insert(dto = dto)
                is BookmarkDTO.LinkBookmarkDTO -> linkBookmarkDAO.insert(dto = dto)
                is BookmarkDTO.ImageBookmarkDTO -> imageBookmarkDAO.insert(dto = dto)
                is BookmarkDTO.RSSFeedBookmarkDTO -> rssFeedBookmarkDAO.insert(dto = dto)
            }
        }
    }

    override suspend fun load(): List<BookmarkDTO> {
        return withContext(context = DispatcherFactory.io()) {
            val texts = textBookmarkDAO.getAll()
            val links = linkBookmarkDAO.getAll()
            val images = imageBookmarkDAO.getAll()
            val rssFeed = rssFeedBookmarkDAO.getAll()

            return@withContext (texts + links + images + rssFeed).sortedByDescending { it.date }
        }
    }

    override suspend fun delete(bookmarkId: Int) {
        withContext(context = DispatcherFactory.io()) {
            textBookmarkDAO.delete(bookmarkId = bookmarkId)
            linkBookmarkDAO.delete(bookmarkId = bookmarkId)
            imageBookmarkDAO.delete(bookmarkId = bookmarkId)
            rssFeedBookmarkDAO.delete(bookmarkId = bookmarkId)
        }
    }

    override suspend fun replaceTopic(topicId: Int, withTopicDTO: TopicDTO) {
        withContext(context = DispatcherFactory.io()) {
            textBookmarkDAO.replaceTopic(topicId = topicId, withTopicDTO = withTopicDTO)
            linkBookmarkDAO.replaceTopic(topicId = topicId, withTopicDTO = withTopicDTO)
            imageBookmarkDAO.replaceTopic(topicId = topicId, withTopicDTO = withTopicDTO)
            rssFeedBookmarkDAO.replaceTopic(topicId = topicId, withTopicDTO = withTopicDTO)
        }
    }
}