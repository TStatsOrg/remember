package com.app.shared.business

import com.app.shared.DefaultTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TopicsUpdateActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions Topics Load Start correctly`() {
        // given
        val topic1 = TopicState(id = 123, name = "News", isEditable = true)
        val topic2 = TopicState(id = 0, name = "General", isEditable = false)

        val state = MainState(
            topics = TopicsState(topics = listOf(topic1, topic2)),
            editBookmark = EditBookmarkState(
                bookmark = BookmarkState.Text(id = 999, text = "Caption", topic = topic1, date = 123L),
                topics = listOf(topic1, topic2)
            ),
            bookmarks = BookmarksState(
                bookmarks = listOf(
                    BookmarkState.Text(id = 999, text = "Caption", topic = topic1, date = 123L),
                    BookmarkState.Link(id = 1000, title = "Title", caption = "Caption", url = "https://my.url.com", topic = topic2, date = 123L, icon = null)
                )
            )
        )
        val action = Actions.Topics.Update(topicId = 123, newName = "New News")

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                topics = TopicsState(topics = listOf(
                    TopicState(id = 123, name = "New News", isEditable = true),
                    topic2
                )),
                editBookmark = EditBookmarkState(
                    bookmark = BookmarkState.Text(
                        id = 999,
                        text = "Caption",
                        topic = TopicState(id = 123, name = "New News", isEditable = true),
                        date = 123L),
                    topics = listOf(TopicState(id = 123, name = "New News", isEditable = true), topic2)
                ),
                bookmarks = BookmarksState(
                    bookmarks = listOf(
                        BookmarkState.Text(id = 999, text = "Caption", topic = TopicState(id = 123, name = "New News", isEditable = true), date = 123L),
                        BookmarkState.Link(id = 1000, title = "Title", caption = "Caption", url = "https://my.url.com", topic = topic2, date = 123L, icon = null)
                    )
                )
            ),
            newState
        )
    }
}