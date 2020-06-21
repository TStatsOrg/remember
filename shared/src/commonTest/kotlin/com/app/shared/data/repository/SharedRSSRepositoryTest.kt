package com.app.shared.data.repository

import com.app.shared.DefaultTest
import com.app.shared.coroutines.runTest
import com.app.shared.data.dao.RSSDAO
import com.app.shared.mocks.MockRSSDTO
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

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
    fun `repository returns both default and user RSS Feeds if it can find them and gives precedence to the ones in the user list`() = runTest {
        // data
        val rss1 = MockRSSDTO(id = 1, title = "Feed 1", description = null, link = "https://rss1/feed.xml", isSubscribed = false)
        val rss2 = MockRSSDTO(id = 2, title = "Feed 2", description = null, link = "https://rss2/feed.xml", isSubscribed = false)
        val rss3 = MockRSSDTO(id = 3, title = "Feed 3", description = null, link = "https://rss3/feed.xml", isSubscribed = false)
        val defaultRss = listOf(rss3, rss1, rss2)

        val rss21 = MockRSSDTO(id = 2, title = "Feed 2", description = null, link = "https://rss2/feed.xml", isSubscribed = true)
        val rss11 = MockRSSDTO(id = 1, title = "Feed 1", description = null, link = "https://rss1/feed.xml", isSubscribed = true)
        val rss4 = MockRSSDTO(id = -5, title = "User Feed", description = null, link = "https://user.rss/feed.xml", isSubscribed = true)
        val userRss = listOf(rss21, rss11, rss4)

        // given
        every { defaultRSSDAO.getAll() } returns defaultRss
        every { userRSSDAO.getAll() } returns userRss

        // when
        val result = repository.getAll()

        // then
        assertEquals(result, listOf(rss4, rss11, rss21, rss3))
    }

    @Test
    fun `repository returns null if it cannot find a RSS Feed with the given ID`() = runTest {
        // given
        every { defaultRSSDAO.get(rssId = 1) } returns null
        every { userRSSDAO.get(rssId = 1) } returns null

        // when
        val result = repository.get(rssId = 1)

        // then
        assertNull(result)
    }

    @Test
    fun `repository returns the RSS Feed with the given ID from the default list if it can find it there but not in the user list`() = runTest {
        // data
        val rss1 = MockRSSDTO(id = 1, title = "Feed 1", description = null, link = "https://rss1/feed.xml", isSubscribed = false)

        // given
        every { defaultRSSDAO.get(rssId = 1) } returns rss1
        every { userRSSDAO.get(rssId = 1) } returns null

        // when
        val result = repository.get(rssId = 1)

        // then
        assertEquals(result, rss1)
    }

    @Test
    fun `repository returns the RSS Feed with the given ID from the user list instead of the default list if it can find it there`() = runTest {
        // data
        val rss1 = MockRSSDTO(id = 1, title = "Feed 1", description = null, link = "https://rss1/feed.xml", isSubscribed = false)
        val rss11 = MockRSSDTO(id = 1, title = "Feed 1", description = null, link = "https://rss1/feed.xml", isSubscribed = true)

        // given
        every { defaultRSSDAO.get(rssId = 1) } returns rss1
        every { userRSSDAO.get(rssId = 1) } returns rss11

        // when
        val result = repository.get(rssId = 1)

        // then
        assertEquals(result, rss11)
    }
}