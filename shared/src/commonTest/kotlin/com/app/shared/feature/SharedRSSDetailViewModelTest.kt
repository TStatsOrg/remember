package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.*
import com.app.shared.coroutines.runTest
import com.app.shared.data.repository.RSSRepository
import com.app.shared.feature.rssdetail.SharedRSSDetailViewModel
import com.app.shared.mocks.MockRSSDTO
import com.app.shared.mocks.MockRSSItemDTO
import com.app.shared.redux.Store
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SharedRSSDetailViewModelTest: DefaultTest() {

    private val store = mockk<Store<MainState>>(relaxed = true)
    private val repository = mockk<RSSRepository>(relaxed = true)

    private val viewModel = SharedRSSDetailViewModel(
        store = store,
        repository = repository
    )

    @Test
    fun `view model will load rss details if a RSS feed with Id is found`() = runTest {
        // given
        val rss = MockRSSDTO(id = 13, title = "My RSS", link = "https://my.rss/feed.xml", description = null, isSubscribed = false)
        val item1 = MockRSSItemDTO(id = 10, link = "https://my.article.1/index.html", title = "My article 1")
        val item2 = MockRSSItemDTO(id = 11, link = "https://my.article.2/index.html", title = "My article 2")

        // when
        coEvery { repository.get(rssId = 13) } returns rss
        coEvery { repository.getAllItems(dto = rss) } returns listOf(item1, item2)
        viewModel.loadRSSFeedData(rssId = 13)

        // then
        verify(exactly = 1) {
            store.dispatch(action = Actions.RSS.Detail.Present(rss = rss))
            store.dispatch(action = Actions.RSS.Detail.LoadItems.Success(items = listOf(item1, item2)))
        }
    }

    @Test
    fun `view model will load rss details if a RSS feed with Id is found but there is a network error`() = runTest {
        // given
        val rss = MockRSSDTO(id = 13, title = "My RSS", link = "https://my.rss/feed.xml", description = null, isSubscribed = false)
        val error = Throwable(message = "Network error")

        // when
        coEvery { repository.get(rssId = 13) } returns rss
        coEvery { repository.getAllItems(dto = rss) } throws error
        viewModel.loadRSSFeedData(rssId = 13)

        // then
        verify(exactly = 1) {
            store.dispatch(action = Actions.RSS.Detail.Present(rss = rss))
            store.dispatch(action = Actions.RSS.Detail.LoadItems.Error(error = error))
        }
    }

    @Test
    fun `view model will not load rss details if a RSS feed with id is not found`() = runTest {
        // given
        //

        // when
        coEvery { repository.get(rssId = 13) } returns null
        viewModel.loadRSSFeedData(rssId = 13)

        // then
        verify(exactly = 0) {
            store.dispatch(action = any())
        }
    }

    @Test
    fun `view model can cleanup after itself`() = runTest {
        // when
        viewModel.cleanup()

        // then
        verify {
            store.remove(observer = any())
        }
    }

    @Test
    fun `view model can observe changes in the RSS state`() = runTest {
        // data
        val rss1 = MockRSSDTO(id = 1, title = "Feed 1", description = null, link = "https://rss1/feed.xml", isSubscribed = false)

        val item1 = MockRSSItemDTO(id = 10, link = "https://my.article.1/index.html", title = "My article 1")
        val item2 = MockRSSItemDTO(id = 11, link = "https://my.article.2/index.html", title = "My article 2")

        // given
        val store = Store(
            reducer = AppStateReducer,
            initialState = MainState()
        )
        val viewModel = SharedRSSDetailViewModel(store = store, repository = repository)

        coEvery { repository.get(rssId = 1) } returns rss1
        coEvery { repository.getAllItems(dto = rss1) } returns listOf(item1, item2)

        // when
        var newState: RSSFeedDetailState? = null
        viewModel.observeRSSDetailsState {
            newState = it
        }

        viewModel.loadRSSFeedData(rssId = 2)

        // then
        assertNull(newState)

        // when
        viewModel.loadRSSFeedData(rssId = 1)

        // then
        assertEquals(
            RSSFeedDetailState(
                feedState = RSSFeedState(
                    id = 1,
                    title = "Feed 1",
                    description = null,
                    link = "https://rss1/feed.xml",
                    isSubscribed = false
                ),
                items = listOf(
                    RSSFeedItemState(
                        id = 10,
                        link = "https://my.article.1/index.html",
                        title = "My article 1",
                        caption = null,
                        pubDate = "20th Apr 2020"
                    ),
                    RSSFeedItemState(
                        id = 11,
                        link = "https://my.article.2/index.html",
                        title = "My article 2",
                        caption = null,
                        pubDate = "20th Apr 2020"
                    )
                )
            ),
            newState
        )
    }
}