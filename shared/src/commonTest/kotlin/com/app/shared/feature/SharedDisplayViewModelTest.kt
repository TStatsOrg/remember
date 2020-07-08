package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.*
import com.app.shared.coroutines.runTest
import com.app.shared.data.capture.HTMLDataProcess
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.feature.display.SharedDisplayViewModel
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class SharedDisplayViewModelTest: DefaultTest() {

    private val store = mockk<Store<MainState>>(relaxed = true)
    private val calendar = mockk<CalendarUtils>(relaxed = true)
    private val bookmarksRepository = mockk<BookmarkRepository>(relaxed = true)
    private val htmlDataProcess = mockk<HTMLDataProcess>(relaxed = true)
    private val viewMode = SharedDisplayViewModel(
        store = store,
        bookmarkRepository = bookmarksRepository,
        calendarUtils = calendar,
        htmlDataProcess = htmlDataProcess
    )

    @Test
    fun `view model can start the load of a feed item or web page`() = runTest {
        // given
        every { calendar.getTime() } returns 123L

        // when
        viewMode.startLoad()

        // then
        verify {
            store.dispatch(action = Actions.Display.Load.Start(time = 123L))
        }
    }

    @Test
    fun `view model can finish the load of a feed item or web page in case of success`() = runTest {
        // given
        val result = HTMLDataProcess.Result(
            title = "My Web Page",
            caption = "My description",
            icon = "https://url.1/icon.jpg"
        )
        every { calendar.getTime() } returns 123L
        every { htmlDataProcess.process(html = "<html><head></head><body>WOW</body></html>") } returns Either.Success(data = result)

        // when
        viewMode.finishLoad(url = "https://my.new.url/index.html", content = "<html><head></head><body>WOW</body></html>")

        // then
        verify {
            store.dispatch(
                action = Actions.Display.Load.Success(
                    url = "https://my.new.url/index.html",
                    title = "My Web Page",
                    caption = "My description",
                    icon = "https://url.1/icon.jpg",
                    time = 123L
                ))
        }
    }

    @Test
    fun `view model can finish the load of a feed item or web page in case of failure`() = runTest {
        // given
        val error = Errors.InvalidHTMLFormat

        every { calendar.getTime() } returns 123L
        every { htmlDataProcess.process(html = "<html><head></head><body>WOW</body></html>") } returns Either.Failure(error = error)

        // when
        viewMode.finishLoad(url = "https://my.new.url/index.html", content = "<html><head></head><body>WOW</body></html>")

        // then
        verify {
            store.dispatch(
                action = Actions.Display.Load.Error(
                    time = 123L,
                    error = error
                ))
        }
    }

    @Test
    fun `view model will not save a new item to bookmarks if it does not already exists in store state`() = runTest {
        // given
        val state = MainState(display = DisplayState())
        every { store.state } returns state

        // when
        viewMode.save()

        // then
        coVerify(exactly = 0) {
            bookmarksRepository.save(dto = any())
            store.dispatch(action = any())
        }
    }

    @Test
    fun `view model can save a new item to bookmarks if it already exists in store state`() = runTest {
        // given
        val linkState = BookmarkState.Link(
            id = 1,
            title = "My title",
            caption = null,
            topic = null,
            isFavourite = false,
            date = 123L,
            icon = null,
            url = "https://my.webpage.com/index.html"
        )
        val state = MainState(
            display = DisplayState(
                item = linkState
            )
        )
        every { store.state } returns state

        // when
        viewMode.save()

        // then
        coVerify {
            bookmarksRepository.save(dto = any())
            store.dispatch(action = any())
        }
    }

    @Test
    fun `view model will not delete an item from bookmarks if it does not already exists in store state`() = runTest {
        // given
        val state = MainState(display = DisplayState())
        every { store.state } returns state

        // when
        viewMode.delete()

        // then
        coVerify(exactly = 0) {
            bookmarksRepository.delete(bookmarkId = 1)
            store.dispatch(action = Actions.Bookmark.Delete(bookmarkId = 1))
        }
    }

    @Test
    fun `view model can delete an item from bookmarks if it already exists in store state`() = runTest {
        // given
        val linkState = BookmarkState.Link(
            id = 1,
            title = "My title",
            caption = null,
            topic = null,
            isFavourite = false,
            date = 123L,
            icon = null,
            url = "https://my.webpage.com/index.html"
        )
        val state = MainState(
            display = DisplayState(
                item = linkState
            )
        )
        every { store.state } returns state

        // when
        viewMode.delete()

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
        viewMode.cleanup()

        // then
        verify {
            store.remove(observer = any())
        }
    }
}