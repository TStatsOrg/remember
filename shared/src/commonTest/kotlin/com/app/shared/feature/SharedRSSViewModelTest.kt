package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.*
import com.app.shared.coroutines.runTest
import com.app.shared.data.repository.RSSRepository
import com.app.shared.feature.rss.SharedRSSViewModel
import com.app.shared.mocks.MockRSSDTO
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
    private val repository = mockk<RSSRepository>(relaxed = true)

    private val viewModel = SharedRSSViewModel(
        store = store,
        calendarUtils = calendar,
        repository = repository
    )

    @Test
    fun `view model can load all RSS items`() = runTest {
        // data
        val dto1 = MockRSSDTO(id = 1, title = "RSS 1", link = "https://rss.1/feed.xml", description = null, isSubscribed = false)
        val dto2 = MockRSSDTO(id = 2, title = "RSS 2", link = "https://rss.2/feed.xml", description = null, isSubscribed = true)

        // given
        every { calendar.getTime() } returns 123L
        coEvery { repository.getAll() } returns listOf(dto1, dto2)

        // when
        viewModel.loadRSSFeeds()

        // then
        verify {
            store.dispatch(action = Actions.RSS.Load.Success(time = 123L, rss = listOf(dto1, dto2)))
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
        val dto1 = MockRSSDTO(id = 1, title = "RSS 1", link = "https://rss.1/feed.xml", description = null, isSubscribed = false)
        val dto2 = MockRSSDTO(id = 2, title = "RSS 2", link = "https://rss.2/feed.xml", description = null, isSubscribed = true)

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
        coEvery { repository.getAll() } returns listOf(dto1, dto2)

        // when
        var newState: RSSState? = null
        viewModel.observeRSSState {
            // then
            newState = it
        }

        viewModel.loadRSSFeeds()

        // then
        assertEquals(
            RSSState(
                feed = listOf(
                    RSSFeedState(
                        id = 1,
                        title = "RSS 1",
                        link = "https://rss.1/feed.xml",
                        description = null,
                        isSubscribed = false
                    ),
                    RSSFeedState(
                        id = 2,
                        title = "RSS 2",
                        link = "https://rss.2/feed.xml",
                        description = null,
                        isSubscribed = true
                    )
                ),
                time = 123L,
                error = null
            ),
            newState
        )
    }
}