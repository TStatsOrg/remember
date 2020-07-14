package com.app.shared.data.repository

import com.app.shared.business.Either
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.data.dao.FeedBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.FeedUpdateDTO
import com.app.shared.data.source.FeedDataSource
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

    override suspend fun getNewContent(): List<FeedUpdateDTO> {
        return withContext(context = DispatcherFactory.io()) {
            val feeds = userFeedsDAO.getAll()//.take(2)
                .map { it.url }
                .map { async { feedDataSource.getLastUpdateDate(url = it) } }
                .toTypedArray()

            val result = awaitAll(*feeds)

            return@withContext result.mapNotNull {
                when (it) {
                    is Either.Success -> it.data
                    else -> null
                }
            }
        }
    }

    override suspend fun update(withNewDates: List<FeedUpdateDTO>) {
        withContext(context = DispatcherFactory.io()) {
            userFeedsDAO.update(withNewDates = withNewDates)
        }
    }
}