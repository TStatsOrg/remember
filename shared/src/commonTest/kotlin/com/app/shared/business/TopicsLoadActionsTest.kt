package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.data.dto.TopicDTO
import com.app.shared.mocks.MockTopicDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class TopicsLoadActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions Topics Load Start correctly`() {
        // given
        val state = MainState()
        val action = Actions.Topics.Load.Start(time = 123L)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(MainState(topics = TopicsState(date = 123L, isLoading = true)), newState)
    }

    @Test
    fun `reducer deals with Actions Topics Load Success correctly`() {
        // given
        val state = MainState()
        val dto = listOf(MockTopicDTO(id = 123, name = "News"), TopicDTO.GeneralTopic())
        val action = Actions.Topics.Load.Success(time = 123L, topics = dto)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(topics = TopicsState(
                date = 123L,
                isLoading = false,
                topics = listOf(
                    TopicState(
                        id = 123,
                        name = "News",
                        isEditable = true),
                    TopicState(
                        id = 0,
                        name = "General",
                        isEditable = false
                    )
                )
            )),
            newState)
    }

    @Test
    fun `reducer deals with Actions Topics Load Error correctly`() {
        // given
        val state = MainState()
        val error = Throwable(message = "Error")
        val action = Actions.Topics.Load.Error(time = 123L, error = error)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(topics = TopicsState(
                date = 123L,
                isLoading = false,
                error = error
            )),
            newState)
    }
}