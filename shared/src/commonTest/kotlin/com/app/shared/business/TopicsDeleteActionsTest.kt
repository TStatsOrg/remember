package com.app.shared.business

import com.app.shared.DefaultTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TopicsDeleteActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions Topics Delete correctly`() {
        // given
        val topic1 = TopicState(id = 123, name = "News", isEditable = true)
        val topic2 = TopicState(id = 0, name = "General", isEditable = false)

        val bookmarks = listOf(
            BookmarkState.Text(id = 999, text = "Caption", topic = topic1, date = 123L),
            BookmarkState.Link(id = 1000, title = "Title", caption = "Caption", url = "https://my.url.com", topic = topic2, date = 123L, icon = null)
        )
        val state = MainState(
            allBookmarks = bookmarks,
            topics = TopicsState(topics = listOf(topic1, topic2)),
            editBookmark = EditBookmarkState(
                bookmark = BookmarkState.Text(id = 999, text = "Caption", topic = topic1, date = 123L),
                topics = listOf(topic1, topic2)
            ),
            bookmarks = BookmarksState(bookmarks = bookmarks)
        )
        val action = Actions.Topics.Delete(topicId = 123)

        // when
        val newState = AppStateReducer(state, action)

        // then
        val newBookmarks = listOf(
            BookmarkState.Text(id = 999, text = "Caption", topic = topic2, date = 123L),
            BookmarkState.Link(id = 1000, title = "Title", caption = "Caption", url = "https://my.url.com", topic = topic2, date = 123L, icon = null)
        )
        assertEquals(
            MainState(
                allBookmarks = newBookmarks,
                topics = TopicsState(topics = listOf(topic2)),
                editBookmark = EditBookmarkState(
                    bookmark = BookmarkState.Text(id = 999, text = "Caption", topic = topic2, date = 123L),
                    topics = listOf(topic2)
                ),
                bookmarks = BookmarksState(bookmarks = newBookmarks)
            ),
            newState
        )
    }
}