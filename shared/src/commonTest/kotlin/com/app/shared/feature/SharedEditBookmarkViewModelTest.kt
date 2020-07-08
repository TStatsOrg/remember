package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.*
import com.app.shared.coroutines.runTest
import com.app.shared.data.dto.TopicDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.feature.editbookmark.SharedEditBookmarkViewModel
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import com.app.shared.utils.toTopicState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.*

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
    fun `view model can load a bookmark to be edited as well as load topics if they are not present`() = runTest {
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

    @Test
    fun `view model can load a bookmark to be edited without loading topics if they are already present`() = runTest {
        // given
        val id = 123
        val topic = mockk<TopicDTO>(relaxed = true)
        val topics = listOf(topic)
        val state = MainState(topics = TopicsState(topics = topics.toTopicState()))
        every { calendar.getTime() } returns 123L
        every { store.state } returns state
        coEvery { topicsRepository.load() } returns listOf()

        // when
        viewModel.loadEditableBookmark(forId = id)

        // then
        verify {
            store.dispatch(action = Actions.Bookmark.Edit(bookmarkId = 123))
        }

        verify(exactly = 0) {
            store.dispatch(action = Actions.Topics.Load.Success(topics = listOf(), time = 123L))
        }
    }

    @Test
    fun `user can observe when a bookmark is edited with a new topic`() = runTest {
        // given
        val oldTopic = TopicState(id = 999, name = "old-topic", isEditable = false)
        val newTopic = TopicState(id = 456, name = "my-topic", isEditable = false)
        val bookmark = BookmarkState.Text(id = 123, date = 0L, topic = oldTopic, text = "some text", isFavourite = false)
        val topics = listOf(oldTopic, newTopic)
        val state = MainState(
            editBookmark = EditBookmarkState(bookmark = bookmark),
            topics = TopicsState(topics = topics)
        )

        every { store.state } returns state

        // when
        viewModel.observeEditBookmarkState {
            // then
            assertEquals(it.bookmark.topic, newTopic)
        }
        viewModel.update(bookmark = 123, withTopic = 456)

        // then
        verify {
            store.dispatch(action = Actions.Bookmark.Update(
                state = BookmarkState.Text(id = 123, date = 0L, topic = newTopic, text = "some text", isFavourite = false)))
        }
    }

    @Test
    fun `user can observe when a bookmark is saved`() = runTest {
        // given
        val topic = TopicState(id = 456, name = "my-topic", isEditable = false)
        val bookmark = BookmarkState.Text(id = 123, date = 0L, topic = topic, text = "some text", isFavourite = false)
        val state = MainState(editBookmark = EditBookmarkState(bookmark = bookmark))

        every { store.state } returns state

        // when
        var isSaved = false
        viewModel.observeBookmarkSaved {
            isSaved = it
        }
        viewModel.save()

        // then
        assertTrue(isSaved)
    }

    @Test
    fun `user will not observe when a bookmark is updated or saved if the view model is cleared`() = runTest {
        // given
        val oldTopic = TopicState(id = 999, name = "old-topic", isEditable = false)
        val newTopic = TopicState(id = 456, name = "my-topic", isEditable = false)
        val bookmark = BookmarkState.Text(id = 123, date = 0L, topic = oldTopic, text = "some text", isFavourite = false)
        val topics = listOf(oldTopic, newTopic)
        val state = MainState(
            editBookmark = EditBookmarkState(bookmark = bookmark),
            topics = TopicsState(topics = topics)
        )

        every { store.state } returns state

        // when
        var isChanged = false
        var isSaved = false
        viewModel.observeEditBookmarkState {
            isChanged = true
        }
        viewModel.observeBookmarkSaved {
            isSaved = true
        }
        viewModel.cleanup()
        viewModel.update(bookmark = 123, withTopic = 456)
        viewModel.save()

        // then
        assertFalse(isChanged)
        assertFalse(isSaved)
    }
}