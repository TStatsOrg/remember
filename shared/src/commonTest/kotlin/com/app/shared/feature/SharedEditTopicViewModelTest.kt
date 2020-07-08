package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.Actions
import com.app.shared.business.MainState
import com.app.shared.coroutines.runTest
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.feature.edittopic.SharedEditTopicViewModel
import com.app.shared.redux.Store
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class SharedEditTopicViewModelTest: DefaultTest() {

    private val store = mockk<Store<MainState>>(relaxed = true)
    private val topicsRepository = mockk<TopicsRepository>(relaxed = true)
    private val viewModel = SharedEditTopicViewModel(store = store, topicsRepository = topicsRepository)

    @Test
    fun `view model can load a topic in edit mode`() = runTest {
        // given

        // when
        viewModel.loadEditableTopic(forTopicId = 1)

        // then
        verify {
            store.dispatch(action = Actions.Topics.Edit(topicId = 1))
        }
    }

    @Test
    fun `view model can update a topic`() = runTest {
        // given

        // when
        viewModel.updateTopic(topicId = 1, name = "New Topic Name")

        // then
        coVerify {
            topicsRepository.save(dto = any())
            store.dispatch(action = Actions.Topics.Update(topicId = 1, newName = "New Topic Name"))
        }
    }

    @Test
    fun `view model can update after itself`() = runTest {
        // given

        // when
        viewModel.cleanup()

        // then
        verify {
            store.remove(observer = any())
        }
    }
}