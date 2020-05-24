package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.data.dto.TopicDTO
import com.app.shared.mocks.MockTopicDTO
import com.app.shared.utils.toState
import kotlin.test.Test
import kotlin.test.assertEquals

class TopicsEditActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions Topics Edit correctly when the topic already exists`() {
        // given
        val dto1 = MockTopicDTO(id = 123, name = "News").toState()
        val dto2 = TopicDTO.GeneralTopic().toState()
        val topics = listOf(dto1, dto2)
        val state = MainState(
            topics = TopicsState(
                topics = topics
            )
        )
        val action = Actions.Topics.Edit(topicId = 123)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                topics = TopicsState(
                    topics = topics
                ),
                editTopic = EditTopicState(
                    topic = TopicState(
                        id = 123,
                        name = "News",
                        isEditable = true
                    )
                )
            ),
            newState
        )
    }

    @Test
    fun `reducer deals with Actions Topics Edit correctly when the topic does not exists`() {
        // given
        val dto1 = MockTopicDTO(id = 123, name = "News").toState()
        val dto2 = TopicDTO.GeneralTopic().toState()
        val topics = listOf(dto1, dto2)
        val state = MainState(
            topics = TopicsState(
                topics = topics
            )
        )
        val action = Actions.Topics.Edit(topicId = 456)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                topics = TopicsState(
                    topics = topics
                ),
                editTopic = null
            ),
            newState
        )
    }
}