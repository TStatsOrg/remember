package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.Actions
import com.app.shared.business.MainState
import com.app.shared.coroutines.runTest
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.feature.hub.SharedHubViewModel
import com.app.shared.mocks.MockBookmarkDTO
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class SharedHubViewModelTest: DefaultTest() {

    private val store = mockk<Store<MainState>>(relaxed = true)
    private val calendar = mockk<CalendarUtils>(relaxed = true)
    private val bookmarksRepository = mockk<BookmarkRepository>(relaxed = true)
    private val viewModel = SharedHubViewModel(
        store = store,
        calendar = calendar,
        bookmarkRepository = bookmarksRepository
    )

    @Test
    fun `hub view model can load all saved data`() = runTest {
        // given
        val dto1 = MockBookmarkDTO.Feed(id = 1, date = 123L, topic = null, url = "https://my.url.1.com", title = null, caption = null, icon = null, isFavourite = true)
        val dto2 = MockBookmarkDTO.Text(id = 2, date = 123L, text = "ABC", topic = null)
        val dto3 = MockBookmarkDTO.Feed(id = 3, date = 123L, topic = null, url = "https://my.url.3.com", title = null, caption = null, icon = null, isFavourite = false)
        val dto4 = MockBookmarkDTO.Image(id = 4, date = 123L, url = "https://my.image.com/image.png", topic = null)
        val dto5 = MockBookmarkDTO.Link(id = 5, url = "https://my.link.com/index.html", caption = null, title = "Article", date = 123L, icon = null, topic = null)

        every { calendar.getTime() } returns 123L
        coEvery { bookmarksRepository.load() } returns listOf(dto1, dto2, dto3, dto4, dto5)

        // when
        viewModel.loadData()

        // then
        verify {
            store.dispatch(action = Actions.Bookmark.Load.Start(time = 123L))
            store.dispatch(action = Actions.Bookmark.Load.Success(time = 123L, bookmarks = listOf(dto1, dto2, dto3, dto4, dto5)))
        }
    }
}