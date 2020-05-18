package com.app.shared.feature

import com.app.shared.business.MainState
import com.app.shared.coroutines.runTest
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.feature.addtopic.SharedAddTopicViewModel
import com.app.shared.redux.Store
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test

class SharedAddTopicViewModelTest {

    private val store = mockk<Store<MainState>>(relaxed = true)
    private val repository = mockk<TopicsRepository>(relaxed = true)

    @Test
    fun `abc`() = runTest {
        // given
        val viewModel = SharedAddTopicViewModel(store = store, topicsRepository = repository)

        // when
        viewModel.addTopic(name = "test")

        // then
        coVerify {
            store.dispatch(action = any())
            repository.save(dto = any())
        }
    }
}