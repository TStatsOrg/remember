package com.app.shared.data.repository

import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.data.dao.RSSDAO
import com.app.shared.data.dto.RSSDTO
import kotlinx.coroutines.withContext

class SharedRSSRepository(
    private val defaultRSSDAO: RSSDAO,
    private val userRSSDAO: RSSDAO
): RSSRepository {

    override suspend fun getAll(): List<RSSDTO> {
        return withContext(DispatcherFactory.io()) {
            val user = userRSSDAO.getAll()
            val userIds = user.map { it.id }
            val defaults = defaultRSSDAO.getAll().filter { !userIds.contains(it.id) }

            return@withContext (defaults + user).sortedBy { it.id }
        }
    }

    override suspend fun get(rssId: Int): RSSDTO? {
        return withContext(DispatcherFactory.io()) {
            val user = userRSSDAO.get(rssId = rssId)
            val default = defaultRSSDAO.get(rssId = rssId)
            return@withContext user ?: default
        }
    }
}