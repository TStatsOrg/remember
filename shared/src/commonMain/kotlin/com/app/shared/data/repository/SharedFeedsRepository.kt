package com.app.shared.data.repository

import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.data.dao.FeedBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO
import kotlinx.coroutines.withContext

class SharedFeedsRepository(
    private val userFeedsDAO: FeedBookmarkDAO,
    private val allFeedsDAO: FeedBookmarkDAO
): FeedsRepository {

    override suspend fun loadAll(): List<BookmarkDTO> {
        return withContext(context = DispatcherFactory.io()) {

            val user = userFeedsDAO.getAll()
            val userIds = user.map { it.id }

            val all = allFeedsDAO.getAll().filter { !userIds.contains(it.id) }

            return@withContext (all + user).sortedBy { it.id }
        }
    }

    override suspend fun get(bookmarkId: Int): BookmarkDTO.FeedBookmarkDTO? {
        return withContext(context = DispatcherFactory.io()) {
            // todo: very inefficient way
            return@withContext loadAll()
                .filterIsInstance<BookmarkDTO.FeedBookmarkDTO>()
                .firstOrNull { it.id == bookmarkId }
        }
    }
}