package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.Actions
import com.app.shared.business.MainState
import com.app.shared.coroutines.runTest
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.feature.bookmarks.SharedBookmarksViewModel
import com.app.shared.mocks.MockBookmarkDTO
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import io.mockk.*
import kotlin.test.Test

class SharedBookmarksViewModelTest: DefaultTest() {

    private val store = mockk<Store<MainState>>(relaxed = true)
    private val calendar = mockk<CalendarUtils>(relaxed = true)
    private val bookmarksRepository = mockk<BookmarkRepository>(relaxed = true)
    private val viewModel = SharedBookmarksViewModel(
        store = store,
        calendar = calendar,
        bookmarkRepository = bookmarksRepository
    )

    @Test
    fun `view model can load all bookmarks that are not feed types`() = runTest {
        // given
        val dto1 = MockBookmarkDTO.Feed(id = 1, date = 123L, topic = null, url = "https://my.url.1.com", title = null, caption = null, icon = null, isFavourite = true)
        val dto2 = MockBookmarkDTO.Text(id = 2, date = 123L, text = "ABC", topic = null)
        val dto3 = MockBookmarkDTO.Feed(id = 3, date = 123L, topic = null, url = "https://my.url.3.com", title = null, caption = null, icon = null, isFavourite = false)
        val dto4 = MockBookmarkDTO.Image(id = 4, date = 123L, url = "https://my.image.com/image.png", topic = null)
        val dto5 = MockBookmarkDTO.Link(id = 5, url = "https://my.link.com/index.html", caption = null, title = "Article", date = 123L, icon = null, topic = null)

        every { calendar.getTime() } returns 123L
        coEvery { bookmarksRepository.load() } returns listOf(dto1, dto2, dto3, dto4, dto5)

        // when
        viewModel.loadBookmarks()

        // then
        verify {
            store.dispatch(action = Actions.Bookmark.Load.Start(time = 123L))
            store.dispatch(action = Actions.Bookmark.Load.Success(time = 123L, bookmarks = listOf(dto2, dto4, dto5)))
        }
    }

    @Test
    fun `view model can search for bookmarks`() = runTest {
        // given

        // when
        viewModel.search(byName = "search term")

        // then
        verify {
            store.dispatch(action = Actions.Bookmark.Search(term = "search term"))
        }
    }

    @Test
    fun `view model can delete bookmarks`() = runTest {
        // given

        // when
        viewModel.delete(bookmarkId = 1)

        // then
        coVerify {
            bookmarksRepository.delete(bookmarkId = 1)
            store.dispatch(action = Actions.Bookmark.Delete(bookmarkId = 1))
        }
    }

    @Test
    fun `view model can cleanup after itself`() = runTest {
        // given

        // when
        viewModel.cleanup()

        // then
        verify {
            store.remove(observer = any())
        }
    }
}