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
            val all = allBookmarkRSSFeedDAO.getAll()
            val user = userBookmarkedRSSFeedDAO.getAll()

            return@withContext (all + user)
        }
    }
}