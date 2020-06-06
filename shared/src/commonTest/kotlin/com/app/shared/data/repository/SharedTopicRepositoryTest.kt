package com.app.shared.data.repository

import com.app.shared.DefaultTest
import com.app.shared.coroutines.runTest
import com.app.shared.data.dao.TopicDAO
import com.app.shared.data.dto.TopicDTO
import com.app.shared.mocks.MockTopicDTO
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals


class SharedTopicRepositoryTest: DefaultTest() {

    private val dao = mockk<TopicDAO>(relaxed = true)
    private val repository = SharedTopicsRepository(topicDAO = dao)

    @Test
    fun `repository can save a topic`() = runTest {
        // given
        val dto = MockTopicDTO(id = 1, name = "my topic")

        // when
        repository.save(dto = dto)

        // then
        verify {
            dao.insert(dto = dto)
        }
    }

    @Test
    fun `repository can return all topics in DAO and the generic one sorted by name`() = runTest {
        // given
        val dto1 = MockTopicDTO(id = 1, name = "XXX")
        val dto2 = MockTopicDTO(id = 2, name = "AAA")

        coEvery { dao.getAll() } returns listOf(dto1, dto2)

        // when
        val result = repository.load()

        // then
        assertEquals(listOf(TopicDTO.GeneralTopic(), dto2, dto1), result)
    }

    @Test
    fun `repository can delete a topic`() = runTest {
        // when
        repository.delete(topicId = 1)

        // then
        verify {
            dao.delete(topicId = 1)
        }
    }
}