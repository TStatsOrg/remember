package com.app.shared.data.repository

import com.app.shared.DefaultTest
import com.app.shared.coroutines.runTest
import com.app.shared.data.dao.FeedBookmarkDAO
import com.app.shared.mocks.MockBookmarkDTO
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SharedFeedsRepositoryTest: DefaultTest() {

    private val userFeedsDAO = mockk<FeedBookmarkDAO>(relaxed = true)
    private val allFeedsDAO = mockk<FeedBookmarkDAO>(relaxed = true)
    private val repository = SharedFeedsRepository(userFeedsDAO = userFeedsDAO, allFeedsDAO = allFeedsDAO)

    @Test
    fun `repository will return all external feeds if user feed is empty`() = runTest {
        // data
        val dto1 = MockBookmarkDTO.Feed(id = 1, date = 123L, topic = null, url = "https://my.url.1.com", title = null, caption = null, icon = null, isFavourite = false)
        val dto2 = MockBookmarkDTO.Feed(id = 2, date = 123L, topic = null, url = "https://my.url.2.com", title = null, caption = null, icon = null, isFavourite = false)

        // given
        every { userFeedsDAO.getAll() } returns listOf()
        every { allFeedsDAO.getAll() } returns listOf(dto1, dto2)

        // when
        val result = repository.loadAll()

        // then
        assertEquals(listOf(dto1, dto2), result)
    }

    @Test
    fun `repository will return all user feeds if external feeds is empty`() = runTest {
        // data
        val dto1 = MockBookmarkDTO.Feed(id = 1, date = 123L, topic = null, url = "https://my.url.1.com", title = null, caption = null, icon = null, isFavourite = false)
        val dto2 = MockBookmarkDTO.Feed(id = 2, date = 123L, topic = null, url = "https://my.url.2.com", title = null, caption = null, icon = null, isFavourite = false)

        // given
        every { userFeedsDAO.getAll() } returns listOf(dto1, dto2)
        every { allFeedsDAO.getAll() } returns listOf()

        // when
        val result = repository.loadAll()

        // then
        assertEquals(listOf(dto1, dto2), result)
    }

    @Test
    fun `repository will eliminate duplicate external feeds if they are found in the user feed as well`() = runTest {
        // data
        val dto1 = MockBookmarkDTO.Feed(id = 1, date = 123L, topic = null, url = "https://my.url.1.com", title = null, caption = null, icon = null, isFavourite = true)
        val dto1_1 = MockBookmarkDTO.Feed(id = 1, date = 123L, topic = null, url = "https://my.url.2.com", title = null, caption = null, icon = null, isFavourite = false)

        // given
        every { userFeedsDAO.getAll() } returns listOf(dto1)
        every { allFeedsDAO.getAll() } returns listOf(dto1_1)

        // when
        val result = repository.loadAll()

        // then
        assertEquals(listOf(dto1), result)
    }

    @Test
    fun `repository will return one item of type feed if it exists`() = runTest {
        // data
        val dto1 = MockBookmarkDTO.Feed(id = 1, date = 123L, topic = null, url = "https://my.url.1.com", title = null, caption = null, icon = null, isFavourite = true)

        // given
        every { userFeedsDAO.getAll() } returns listOf(dto1)
        every { allFeedsDAO.getAll() } returns listOf()

        // when
        val result = repository.get(bookmarkId = 1)

        // then
        assertEquals(dto1, result)
    }

    @Test
    fun `repository will return null if no feed exists with corresponding ID`() = runTest {
        // data
        val dto1 = MockBookmarkDTO.Feed(id = 1, date = 123L, topic = null, url = "https://my.url.1.com", title = null, caption = null, icon = null, isFavourite = true)

        // given
        every { userFeedsDAO.getAll() } returns listOf(dto1)
        every { allFeedsDAO.getAll() } returns listOf()

        // when
        val result = repository.get(bookmarkId = 5)

        // then
        assertNull(result)
    }
}