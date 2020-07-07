package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.*
import com.app.shared.coroutines.runTest
import com.app.shared.data.repository.RSSFeedBookmarkRepository
import com.app.shared.feature.rss.SharedRSSViewModel
import com.app.shared.mocks.MockBookmarkDTO
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class SharedRSSViewModelTest: DefaultTest() {

    private val store = mockk<Store<MainState>>(relaxed = true)
    private val calendar = mockk<CalendarUtils>(relaxed = true)
    private val repository = mockk<RSSFeedBookmarkRepository>(relaxed = true)

    private val viewModel = SharedRSSViewModel(
        store = store,
        calendarUtils = calendar,
        repository = repository
    )

    @Test
    fun `view model can load all RSS items`() = runTest {
        // data
        val dto1 = MockBookmarkDTO.RSSFeed(id = 1, title = "My RSS 1", url = "https://feed.1/feed.xml", icon = null, isSubscribed = true, date = 1, caption = null, topic = null)
        val dto2 = MockBookmarkDTO.RSSFeed(id = 2, title = "My RSS 2", url = "https://feed.2/feed.xml", icon = null, isSubscribed = false, date = 2, caption = null, topic = null)

        // given
        every { calendar.getTime() } returns 123L
        coEvery { repository.loadAll() } returns listOf(dto1, dto2)

        // when
        viewModel.loadRSSFeeds()

        // then
        verify {
            store.dispatch(action = Actions.Feeds.Load.Start(time = 123L))
            store.dispatch(action = Actions.Feeds.Load.Success(feeds = listOf(dto1, dto2), time = 123L))
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
        val dto1 = MockBookmarkDTO.RSSFeed(id = 1, title = "My RSS 1", url = "https://feed.1/feed.xml", icon = null, isSubscribed = true, date = 1, caption = null, topic = null)
        val dto2 = MockBookmarkDTO.RSSFeed(id = 2, title = "My RSS 2", url = "https://feed.2/feed.xml", icon = null, isSubscribed = false, date = 2, caption = null, topic = null)

        val store = Store(
            reducer = AppStateReducer,
            initialState = MainState()
        )
        val viewModel = SharedRSSViewModel(
            store = store,
            repository = repository,
            calendarUtils = calendar
        )

        // given
        every { calendar.getTime() } returns 123L
        coEvery { repository.loadAll() } returns listOf(dto1, dto2)

        // when
        var newState: FeedsState? = null
        viewModel.observeRSSState {
            // then
            newState = it
        }

        viewModel.loadRSSFeeds()

        // then
        assertEquals(
            FeedsState(
                feeds = listOf(
                    BookmarkState.RSSFeed(
                        id = 1,
                        title = "My RSS 1",
                        url = "https://feed.1/feed.xml",
                        icon = null,
                        isSubscribed = true,
                        date = 1,
                        caption = null,
                        topic = null
                    ),
                    BookmarkState.RSSFeed(
                        id = 2,
                        title = "My RSS 2",
                        url = "https://feed.2/feed.xml",
                        icon = null,
                        isSubscribed = false,
                        date = 2,
                        caption = null,
                        topic = null
                    )
                )
            ),
            newState
        )
    }
}