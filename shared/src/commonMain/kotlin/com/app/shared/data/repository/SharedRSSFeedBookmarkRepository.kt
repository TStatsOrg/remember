package com.app.shared.data.repository

import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.data.dao.RSSFeedBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO
import kotlinx.coroutines.withContext

class SharedRSSFeedBookmarkRepository(
    private val userBookmarkedRSSFeedDAO: RSSFeedBookmarkDAO,
    private val allBookmarkRSSFeedDAO: RSSFeedBookmarkDAO
): RSSFeedBookmarkRepository {

    override suspend fun loadAll(): List<BookmarkDTO> {
        return withContext(context = DispatcherFactory.io()) {

            val user = userBookmarkedRSSFeedDAO.getAll().map { dto ->
                return@map UserBookmarkedRSSFeed(
                    id = dto.id,
                    date = dto.date,
                    topic = dto.topic,
                    url = dto.url,
                    title = dto.title,
                    caption = dto.caption,
                    icon = dto.icon
                )
            }
            val userIds = user.map { it.id }

            val all = allBookmarkRSSFeedDAO.getAll().filter { !userIds.contains(it.id) }

            return@withContext (all + user).sortedBy { it.id }
        }
    }

    override suspend fun get(bookmarkId: Int): BookmarkDTO.RSSFeedBookmarkDTO? {
        return withContext(context = DispatcherFactory.io()) {
            // todo: very inefficient way
            return@withContext loadAll()
                .filterIsInstance<BookmarkDTO.RSSFeedBookmarkDTO>()
                .firstOrNull { it.id == bookmarkId }
        }
    }

    private data class UserBookmarkedRSSFeed(
        override val id: Int,
        override val date: Long,
        override val topic: TopicDTO?,
        override val url: String,
        override val title: String?,
        override val caption: String?,
        override val icon: String?,
        override val isSubscribed: Boolean = true
    ) : BookmarkDTO.RSSFeedBookmarkDTO
}