package com.app.shared.data.repository

import com.app.shared.business.Either
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.data.dao.FeedBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.source.FeedDataSource
import com.app.shared.utils.MLogger
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class SharedFeedsRepository(
    private val userFeedsDAO: FeedBookmarkDAO,
    private val allFeedsDAO: FeedBookmarkDAO,
    private val feedDataSource: FeedDataSource
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

    override suspend fun getNewContent() {
        withContext(context = DispatcherFactory.io()) {
            val feeds = userFeedsDAO.getAll().take(2)
                .map { it.url }
                .map { async { feedDataSource.getLastUpdateDate(url = it) } }
                .toTypedArray()

            val result = awaitAll(*feeds)

            val unpacked = result.mapNotNull {
                when (it) {
                    is Either.Success -> it.data
                    else -> null
                }
            }

            MLogger.log("GABBOX ==> feeds are $unpacked")
        }
    }
}