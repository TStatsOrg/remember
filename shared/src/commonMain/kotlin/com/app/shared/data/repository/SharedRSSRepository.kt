package com.app.shared.data.repository

import com.app.shared.business.Either
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.data.dao.RSSDAO
import com.app.shared.data.dto.RSSDTO
import com.app.shared.data.dto.RSSItemDTO
import com.app.shared.data.source.RSSItemDataSource
import kotlinx.coroutines.withContext

class SharedRSSRepository(
    private val defaultRSSDAO: RSSDAO,
    private val userRSSDAO: RSSDAO,
    private val rssItemDataSource: RSSItemDataSource
): RSSRepository {

    override suspend fun getAll(): List<RSSDTO> {
        return withContext(context = DispatcherFactory.io()) {
            val user = userRSSDAO.getAll()
            val userIds = user.map { it.id }
            val defaults = defaultRSSDAO.getAll().filter { !userIds.contains(it.id) }

            return@withContext (defaults + user).sortedBy { it.id }
        }
    }

    override suspend fun get(rssId: Int): RSSDTO? {
        return withContext(context = DispatcherFactory.io()) {
            val user = userRSSDAO.get(rssId = rssId)
            val default = defaultRSSDAO.get(rssId = rssId)
            return@withContext user ?: default
        }
    }

    override suspend fun getAllItems(dto: RSSDTO): Either<List<RSSItemDTO>> {
        return withContext(context = DispatcherFactory.io()) {

            val data = rssItemDataSource.getRSSItems(fromLink = dto.link)

            return@withContext when (data) {
                is Either.Success -> Either.Success<List<RSSItemDTO>>(data = data.data.sortedBy { it.title })
                is Either.Failure -> Either.Failure<List<RSSItemDTO>>(error = data.error)
            }
        }
    }

    override suspend fun subscribe(rssId: Int) {
        withContext(context = DispatcherFactory.io()) {

            val item = get(rssId = rssId)

            item?.let {
                userRSSDAO.insert(dto = it)
            }
        }
    }

    override suspend fun unsubscribe(rssId: Int) {
        withContext(context = DispatcherFactory.io()) {
            userRSSDAO.delete(rssId = rssId)
        }
    }
}