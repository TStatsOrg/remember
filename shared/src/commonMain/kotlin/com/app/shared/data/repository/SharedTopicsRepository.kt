package com.app.shared.data.repository

import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.data.dao.TopicDAO
import com.app.shared.data.dto.TopicDTO
import kotlinx.coroutines.withContext

class SharedTopicsRepository(
    private val topicDAO: TopicDAO
): TopicsRepository {

    override suspend fun save(dto: TopicDTO) {
        withContext(context = DispatcherFactory.io()) {
            topicDAO.insert(dto = dto)
        }
    }

    override suspend fun load(): List<TopicDTO> {
        return withContext(context = DispatcherFactory.io()) {
            val userTopics = topicDAO.getAll().sortedBy { it.name }
            val defaultTopic = getDefaultTopics()

            return@withContext (defaultTopic + userTopics).distinctBy { it.id }
        }
    }

    private fun getDefaultTopics() = listOf(TopicDTO.GeneralTopic())

    override suspend fun delete(topicId: Int) {
        withContext(context = DispatcherFactory.io()) {
            topicDAO.delete(topicId = topicId)
        }
    }
}