package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.*
import com.app.shared.coroutines.runTest
import com.app.shared.data.repository.RSSRepository
import com.app.shared.feature.rssdetail.SharedRSSDetailViewModel
import com.app.shared.mocks.MockRSSDTO
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

        // when
        coEvery { repository.get(rssId = 13) } returns rss
        viewModel.loadRSSFeedData(rssId = 13)

        // then
        verify(exactly = 1) {
            store.dispatch(action = Actions.RSS.Detail.Present(rss = rss))
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
        
        // given
        val store = Store(
            reducer = AppStateReducer,
            initialState = MainState()
        )
        val viewModel = SharedRSSDetailViewModel(store = store, repository = repository)

        coEvery { repository.get(rssId = 1) } returns rss1

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
                )
            ),
            newState
        )
    }
}