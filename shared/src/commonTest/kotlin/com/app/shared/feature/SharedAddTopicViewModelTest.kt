package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.MainState
import com.app.shared.coroutines.runTest
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.feature.addtopic.SharedAddTopicViewModel
import com.app.shared.redux.Store
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SharedAddTopicViewModelTest: DefaultTest() {

    private val store = mockk<Store<MainState>>(relaxed = true)
    private val repository = mockk<TopicsRepository>(relaxed = true)

    // to test
    private val viewModel = SharedAddTopicViewModel(store = store, topicsRepository = repository)

    @Test
    fun `view model adds a new topic if name is not empty`() = runTest {
        // given
        val name = "test"

        // when
        viewModel.addTopic(name = name)

        // then
        coVerify {
            store.dispatch(action = any())
            repository.save(dto = any())
        }
    }

    @Test
    fun `view model does not add a new topic if topic name is empty`() = runTest {
        // given
        val name = ""

        // when
        viewModel.addTopic(name = name)

        // then
        coVerify(exactly = 0) {
            store.dispatch(action = any())
            repository.save(dto = any())
        }
    }

    @Test
    fun `user can observe when a new topic is added`() = runTest {
        // given
        val name = "test"

        // when
        var success = false
        viewModel.observeTopicSaved {
            success = it
        }
        viewModel.addTopic(name = name)

        // then
        assertTrue(success)
    }

    @Test
    fun `user will not observe a new topic being added if the topic name is empty`() = runTest {
        // given
        val name = ""

        // when
        var success = false
        viewModel.observeTopicSaved {
            success = it
        }
        viewModel.addTopic(name = name)

        // then
        assertFalse(success)
    }

    @Test
    fun `user will not observe a new topic being added if the view model is cleaned up`() = runTest {
        // given
        val name = ""

        // when
        var success = false
        viewModel.observeTopicSaved {
            success = it
        }
        viewModel.cleanup()
        viewModel.addTopic(name = name)

        // then
        assertFalse(success)
    }
}