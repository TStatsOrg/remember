package com.app.shared.data.repository

import com.app.shared.DefaultTest
import com.app.shared.coroutines.runTest
import com.app.shared.data.dao.RSSDAO
import com.app.shared.mocks.MockRSSDTO
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

class SharedRSSRepositoryTest: DefaultTest() {

    private val defaultRSSDAO = mockk<RSSDAO>()
    private val userRSSDAO = mockk<RSSDAO>()
    private val repository = SharedRSSRepository(defaultRSSDAO, userRSSDAO)

    @Test
    fun `repository returns only default RSS items that come with the app sorted by id asc`() = runTest {
        // data
        val rss1 = MockRSSDTO(id = 1, title = "Feed 1", description = null, link = "https://rss1/feed.xml", isSubscribed = false)
        val rss2 = MockRSSDTO(id = 2, title = "Feed 2", description = null, link = "https://rss2/feed.xml", isSubscribed = false)
        val rss3 = MockRSSDTO(id = 3, title = "Feed 3", description = null, link = "https://rss3/feed.xml", isSubscribed = false)
        val defaultRss = listOf(rss3, rss1, rss2)

        // given
        every { defaultRSSDAO.getAll() } returns defaultRss
        every { userRSSDAO.getAll() } returns listOf()

        // when
        val result = repository.getAll()

        // then
        assertEquals(result, listOf(rss1, rss2, rss3))
    }

    @Test
    fun `repository returns default and user saved RSS items sorted by id asc`() = runTest {
        // data
        val rss1 = MockRSSDTO(id = 1, title = "Feed 1", description = null, link = "https://rss1/feed.xml", isSubscribed = false)
        val rss2 = MockRSSDTO(id = 2, title = "Feed 2", description = null, link = "https://rss2/feed.xml", isSubscribed = false)
        val rss3 = MockRSSDTO(id = 3, title = "Feed 3", description = null, link = "https://rss3/feed.xml", isSubscribed = false)
        val defaultRss = listOf(rss3, rss1, rss2)

        val rss5 = rss2.copy(isSubscribed = true)
        val rss6 = rss1.copy(isSubscribed = true)
        val rss7 = MockRSSDTO(id = -5, title = "User Feed", description = null, link = "https://user.rss/feed.xml", isSubscribed = true)
        val userRss = listOf(rss5, rss6, rss7)

        // given
        every { defaultRSSDAO.getAll() } returns defaultRss
        every { userRSSDAO.getAll() } returns userRss

        // when
        val result = repository.getAll()

        // then
        assertEquals(result, listOf(rss7, rss6, rss5, rss3))
    }
}