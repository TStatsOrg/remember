package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.Actions
import com.app.shared.business.MainState
import com.app.shared.coroutines.runTest
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.feature.editbookmark.SharedEditBookmarkViewModel
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class SharedEditBookmarkViewModelTest: DefaultTest() {

    private val store = mockk<Store<MainState>>(relaxed = true)
    private val calendar = mockk<CalendarUtils>(relaxed = true)
    private val bookmarkRepository = mockk<BookmarkRepository>(relaxed = true)
    private val topicsRepository = mockk<TopicsRepository>(relaxed = true)

    // to test
    private val viewModel = SharedEditBookmarkViewModel(
        store = store,
        calendar = calendar,
        bookmarkRepository = bookmarkRepository,
        topicsRepository = topicsRepository
    )

    @Test
    fun `view model can load a bookmark to be edited`() = runTest {
        // given
        val id = 123
        every { calendar.getTime() } returns 123L
        every { store.state } returns MainState()
        coEvery { topicsRepository.load() } returns listOf()

        // when
        viewModel.loadEditableBookmark(forId = id)

        // then
        verify {
            store.dispatch(action = Actions.Topics.Load.Success(topics = listOf(), time = 123L))
            store.dispatch(action = Actions.Bookmark.Edit(bookmarkId = 123))
        }
    }
}