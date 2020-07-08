package com.app.shared.data.repository

import com.app.shared.DefaultTest
import com.app.shared.business.Either
import com.app.shared.business.Errors
import com.app.shared.coroutines.runTest
import com.app.shared.data.dto.FeedItemDTO
import com.app.shared.data.source.FeedItemDataSource
import com.app.shared.mocks.MockFeedItemDTO
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

class SharedFeedItemRepositoryTest: DefaultTest() {

    private val dataSource = mockk<FeedItemDataSource>(relaxed = true)
    private val repository = SharedFeedItemRepository(dataSource = dataSource)

    @Test
    fun `repository returns a success result with all feed items for a particular feed in case of success`() = runTest {
        // given
        val dto1 = MockFeedItemDTO(id = 1, link = "https://url.1.com/index.html", title = "Website 1")
        val dto2 = MockFeedItemDTO(id = 2, link = "https://url.2.com/index.html", title = "Website 2")
        every { dataSource.getItems(fromLink = "https://my.feed.com/feed.xml") } returns Either.Success(data = listOf(dto1, dto2))

        // when
        val result = repository.getAllItems(url = "https://my.feed.com/feed.xml")

        // then
        val expandedResult = result as? Either.Success<List<FeedItemDTO>>
        val data = expandedResult?.data
        assertEquals(listOf(dto1, dto2), data)
    }

    @Test
    fun `repository returns an error result for a particular feed in case of error`() = runTest {
        // given
        val error = Errors.InvalidFeedFormat
        every { dataSource.getItems(fromLink = "https://my.feed.com/feed.xml") } returns Either.Failure(error = error)

        // when
        val result = repository.getAllItems(url = "https://my.feed.com/feed.xml")

        // then
        val expandedResult = result as? Either.Failure<List<FeedItemDTO>>
        val data = expandedResult?.error
        assertEquals(error, data)
    }
}