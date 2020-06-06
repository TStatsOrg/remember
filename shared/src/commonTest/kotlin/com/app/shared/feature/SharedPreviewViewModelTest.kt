package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.Actions
import com.app.shared.business.BookmarkState
import com.app.shared.business.MainState
import com.app.shared.business.PreviewState
import com.app.shared.coroutines.runTest
import com.app.shared.data.capture.RawDataCapture
import com.app.shared.data.capture.RawDataProcess
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.feature.preview.SharedPreviewViewModel
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class SharedPreviewViewModelTest: DefaultTest() {

    private val store = mockk<Store<MainState>>(relaxed = true)
    private val calendar = mockk<CalendarUtils>(relaxed = true)
    private val repository = mockk<BookmarkRepository>(relaxed = true)
    private val capture = mockk<RawDataCapture<String>>(relaxed = true)
    private val process = mockk<RawDataProcess>(relaxed = true)

    // to test
    private val viewModel = SharedPreviewViewModel(
        store = store,
        calendar = calendar,
        bookmarkRepository = repository,
        capture = capture,
        process = process
    )

    @Test
    fun `view model ca present raw data as a bookmark`() = runTest {
        // given
        val rawData = "https://my.website.com/article.01.html"
        val result = RawDataProcess.Item.Link(
            url = "https://my.website.com/article.01.html",
            title = "My article",
            description = "This is so cool",
            icon = "https://my.website.com/article.image.jpg"
        )

        every { calendar.getTime() } returns 123L
        every { capture.capture(input = "https://my.website.com/article.01.html", data = any()) } answers {
            secondArg<(String?)->Unit>().invoke(rawData)
        }
        every { process.process(capture = "https://my.website.com/article.01.html", result = any()) } answers {
            secondArg<(RawDataProcess.Item) -> Unit>().invoke(result)
        }

        // when
        viewModel.capture(input = "https://my.website.com/article.01.html")

        // then
        verify {
            store.dispatch(action = Actions.Bookmark.Preview.Reset)
            store.dispatch(action = Actions.Bookmark.Preview.Start)
            store.dispatch(action = any())
        }
    }

    @Test
    fun `view model can save if there is a previewed bookmark`() = runTest {
        // given
        every { store.state } returns MainState(
            preview = PreviewState(
                preview = BookmarkState.Text(
                    id = 1,
                    text = "My text",
                    date = 123,
                    topic = null
                )
            )
        )
        coEvery { repository.save(dto = any()) } returns Unit

        // when
        viewModel.save()

        // then
        verify {
            store.dispatch(action = any())
        }
    }

    @Test
    fun `view model will not save if there is no previewed bookmark`() = runTest {
        // given
        every { store.state } returns MainState()
        coEvery { repository.save(dto = any()) } returns Unit

        // when
        viewModel.save()

        // then
        verify(exactly = 0) {
            store.dispatch(action = any())
        }
    }
}