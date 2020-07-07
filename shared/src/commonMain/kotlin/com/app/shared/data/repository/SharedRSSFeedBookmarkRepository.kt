package com.app.shared.data.repository

import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.data.dao.RSSFeedBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO
import kotlinx.coroutines.withContext

class SharedRSSFeedBookmarkRepository(
    private val userBookmarkedRSSFeedDAO: RSSFeedBookmarkDAO,
    private val allBookmarkRSSFeedDAO: RSSFeedBookmarkDAO
): RSSFeedBookmarkRepository {

    override suspend fun loadAll(): List<BookmarkDTO> {
        return withContext(context = DispatcherFactory.io()) {

            val user = userBookmarkedRSSFeedDAO.getAll()
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
}