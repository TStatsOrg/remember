package com.app.shared.data.repository

import com.app.shared.DefaultTest
import com.app.shared.business.Either
import com.app.shared.coroutines.runTest
import com.app.shared.data.dao.RSSDAO
import com.app.shared.data.source.RSSItemDataSource
import com.app.shared.mocks.MockRSSDTO
import com.app.shared.mocks.MockRSSItemDTO
import io.mockk.every
import io.mockk.mockk
import kotlin.test.*

class SharedRSSRepositoryTest: DefaultTest() {

    private val defaultRSSDAO = mockk<RSSDAO>()
    private val userRSSDAO = mockk<RSSDAO>()
    private val dataSource = mockk<RSSItemDataSource>(relaxed = true)
    private val repository = SharedRSSRepository(
        defaultRSSDAO = defaultRSSDAO,
        userRSSDAO = userRSSDAO,
        rssItemDataSource = dataSource)

    @Test
    fun `repository returns only default RSS items that come with the app sorted by id asc`() = runTest {
        // data
        val rss1 = MockRSSDTO(id = 1, title = "Feed 1", caption = null, link = "https://rss1/feed.xml", isSubscribed = false)
        val rss2 = MockRSSDTO(id = 2, title = "Feed 2", caption = null, link = "https://rss2/feed.xml", isSubscribed = false)
        val rss3 = MockRSSDTO(id = 3, title = "Feed 3", caption = null, link = "https://rss3/feed.xml", isSubscribed = false)
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
        val rss1 = MockRSSDTO(id = 1, title = "Feed 1", caption = null, link = "https://rss1/feed.xml", isSubscribed = false)
        val rss2 = MockRSSDTO(id = 2, title = "Feed 2", caption = null, link = "https://rss2/feed.xml", isSubscribed = false)
        val rss3 = MockRSSDTO(id = 3, title = "Feed 3", caption = null, link = "https://rss3/feed.xml", isSubscribed = false)
        val defaultRss = listOf(rss3, rss1, rss2)

        val rss21 = MockRSSDTO(id = 2, title = "Feed 2", caption = null, link = "https://rss2/feed.xml", isSubscribed = true)
        val rss11 = MockRSSDTO(id = 1, title = "Feed 1", caption = null, link = "https://rss1/feed.xml", isSubscribed = true)
        val rss4 = MockRSSDTO(id = -5, title = "User Feed", caption = null, link = "https://user.rss/feed.xml", isSubscribed = true)
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
        val rss1 = MockRSSDTO(id = 1, title = "Feed 1", caption = null, link = "https://rss1/feed.xml", isSubscribed = false)

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
        val rss1 = MockRSSDTO(id = 1, title = "Feed 1", caption = null, link = "https://rss1/feed.xml", isSubscribed = false)
        val rss11 = MockRSSDTO(id = 1, title = "Feed 1", caption = null, link = "https://rss1/feed.xml", isSubscribed = true)

        // given
        every { defaultRSSDAO.get(rssId = 1) } returns rss1
        every { userRSSDAO.get(rssId = 1) } returns rss11

        // when
        val result = repository.get(rssId = 1)

        // then
        assertEquals(result, rss11)
    }

    @Test
    fun `repository returns RSS Feed Items for a particular RSS Feed`() = runTest {
        // data
        val rss = MockRSSDTO(id = 1, title = "Feed 1", caption = null, link = "https://rss1/feed.xml", isSubscribed = false)

        val item1 = MockRSSItemDTO(id = 10, link = "https://my.article.1/index.html", title = "My article 1")
        val item2 = MockRSSItemDTO(id = 11, link = "https://my.article.2/index.html", title = "My article 2")

        // given
        every { dataSource.getRSSItems(fromLink = "https://rss1/feed.xml") } returns Either.Success(listOf(item1, item2))

        // when
        val result = repository.getAllItems(dto = rss)
        val unpacked = result as Either.Success

        // then
        assertNotNull(unpacked)
        assertEquals(unpacked.data, listOf(item1, item2))
    }
}