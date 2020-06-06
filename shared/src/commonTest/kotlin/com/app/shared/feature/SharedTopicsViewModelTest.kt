package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.Actions
import com.app.shared.business.AppStateReducer
import com.app.shared.business.MainState
import com.app.shared.business.TopicState
import com.app.shared.coroutines.runTest
import com.app.shared.data.dto.TopicDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.feature.topics.SharedTopicsViewModel
import com.app.shared.mocks.MockTopicDTO
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class SharedTopicsViewModelTest: DefaultTest() {

//    private val store = Store(reducer = AppStateReducer, initialState = MainState())
    private val store = mockk<Store<MainState>>(relaxed = true)
    private val bookmarkRepository = mockk<BookmarkRepository>(relaxed = true)
    private val topicsRepository = mockk<TopicsRepository>(relaxed = true)
    private val calendarUtils = mockk<CalendarUtils>(relaxed = true)
    private val viewModel = SharedTopicsViewModel(
        store = store,
        topicsRepository = topicsRepository,
        bookmarkRepository = bookmarkRepository,
        calendar = calendarUtils
    )

    @Test
    fun `view model can load tests correctly`() = runTest {
        // given
        val topic1 = MockTopicDTO(id = 1, name = "my-topic-1")
        val topic2 = MockTopicDTO(id = 2, name = "my-topic-2")
        every { calendarUtils.getTime() } returns 123L
        coEvery { topicsRepository.load() } returns listOf(topic1, topic2)

        // when
        viewModel.loadTopics()

        // then
        verify {
            store.dispatch(action = Actions.Topics.Load.Start(time = 123L))
            store.dispatch(action = Actions.Topics.Load.Success(time = 123L, topics = listOf(topic1, topic2)))
        }
    }

    @Test
    fun `view model can filter correctly`() = runTest {
        // given
        val state = TopicState(id = 1, name = "my-topic-1", isEditable = true)

        // when
        viewModel.filter(byTopic = state)

        // then
        verify {
            store.dispatch(action = Actions.Bookmark.Filter(topic = state))
        }
    }

    @Test
    fun `view model cannot delete the general default topic`() = runTest {
        // given

        // when
        viewModel.delete(topicId = 0)

        // then
        verify(exactly = 0) {
            store.dispatch(action = Actions.Topics.Delete(topicId = 0))
        }
    }

    @Test
    fun `view model can delete any other topic`() = runTest {
        // given
        val defaultTopic = TopicDTO.GeneralTopic()
        coEvery { bookmarkRepository.replaceTopic(topicId = 1, withTopicDTO = defaultTopic) } returns Unit
        coEvery { topicsRepository.delete(topicId = 1) } returns Unit

        // when
        viewModel.delete(topicId = 1)

        // then
        verify {
            store.dispatch(action = Actions.Topics.Delete(topicId = 1))
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
}