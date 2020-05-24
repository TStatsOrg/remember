package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.data.dto.TopicDTO
import com.app.shared.mocks.MockTopicDTO
import com.app.shared.utils.toState
import kotlin.test.Test
import kotlin.test.assertEquals

class TopicsAddActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions Topics Add correctly`() {
        // given
        val dto1 = MockTopicDTO(id = 123, name = "News").toState()
        val dto2 = TopicDTO.GeneralTopic().toState()
        val topics = listOf(dto1, dto2)
        val state = MainState(
            topics = TopicsState(
                date = 123L,
                topics = topics
            ),
            editBookmark = EditBookmarkState(
                bookmark = BookmarkState.Text(id = 111, text = "Some text", topic = dto2, date = 123L),
                topics = topics
            )
        )
        val dto = MockTopicDTO(id = 456, name = "My topic")
        val action = Actions.Topics.Add(topic = dto)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                topics = TopicsState(
                    topics = listOf(TopicState(id = 456, name = "My topic", isEditable = true), dto1, dto2)
                ),
                editBookmark = EditBookmarkState(
                    bookmark = BookmarkState.Text(id = 111, text = "Some text", topic = dto2, date = 123L),
                    topics = listOf(TopicState(id = 456, name = "My topic", isEditable = true), dto1, dto2)
                )
            ),
            newState
        )
    }
}