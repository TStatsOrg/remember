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
            return@withContext rssItemDataSource.getRSSItems(fromLink = dto.link)
        }
    }
}