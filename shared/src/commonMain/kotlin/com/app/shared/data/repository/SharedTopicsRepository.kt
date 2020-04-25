package com.app.shared.data.repository

import com.app.shared.coroutines.IODispatcher
import com.app.shared.data.dao.TopicDAO
import com.app.shared.data.dto.TopicDTO
import kotlinx.coroutines.withContext

class SharedTopicsRepository(
    private val topicDAO: TopicDAO
): TopicsRepository {

    override suspend fun save(dto: TopicDTO) {
        withContext(context = IODispatcher) {
            topicDAO.insert(dto = dto)
        }
    }

    override suspend fun load(): List<TopicDTO> {
        return withContext(context = IODispatcher) {
            val userTopics = topicDAO.getAll().sortedBy { it.name }
            val defaultTopic = getDefaultTopics()

            return@withContext (defaultTopic + userTopics)
        }
    }

    private fun getDefaultTopics() = listOf(TopicDTO.GeneralTopic())
}