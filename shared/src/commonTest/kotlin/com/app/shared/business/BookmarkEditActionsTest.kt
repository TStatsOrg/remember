package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.data.dto.TopicDTO
import com.app.shared.mocks.MockTopicDTO
import com.app.shared.utils.toState
import kotlin.test.Test
import kotlin.test.assertEquals

class BookmarkEditActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions Bookmark Edit correctly with no preview`() {
        // given
        val bookmark1 = BookmarkState.Text(id = 1, text = "Text", topic = null, date = 123)
        val bookmark2 = BookmarkState.Image(id = 2, url = "https://my.cdn/image.png", topic = null, date = 123)
        val bookmarks = listOf(bookmark1, bookmark2)

        val dto1 = MockTopicDTO(id = 123, name = "News").toState()
        val dto2 = TopicDTO.GeneralTopic().toState()
        val topics = listOf(dto1, dto2)

        val state = MainState(
            allBookmarks = bookmarks,
            bookmarks = BookmarksState(bookmarks = bookmarks),
            topics = TopicsState(topics = topics)
        )

        // when
        val action = Actions.Bookmark.Edit(bookmarkId = 2)
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                allBookmarks = bookmarks,
                bookmarks = BookmarksState(bookmarks = bookmarks),
                topics = TopicsState(topics = topics),
                editBookmark = EditBookmarkState(bookmark = bookmark2, topics = topics)
            ),
            newState
        )
    }

    @Test
    fun `reducer deals with Actions Bookmark Edit correctly from preview`() {
        // given
        val bookmark1 = BookmarkState.Text(id = 1, text = "Text", topic = null, date = 123)
        val bookmark2 = BookmarkState.Image(id = 2, url = "https://my.cdn/image.png", topic = null, date = 123)
        val bookmarks = listOf(bookmark1)

        val dto1 = MockTopicDTO(id = 123, name = "News").toState()
        val dto2 = TopicDTO.GeneralTopic().toState()
        val topics = listOf(dto1, dto2)

        val state = MainState(
            allBookmarks = bookmarks,
            bookmarks = BookmarksState(bookmarks = bookmarks),
            preview = PreviewState(preview = bookmark2),
            topics = TopicsState(topics = topics)
        )

        // when
        val action = Actions.Bookmark.Edit(bookmarkId = 2)
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                allBookmarks = bookmarks,
                bookmarks = BookmarksState(bookmarks = bookmarks),
                topics = TopicsState(topics = topics),
                preview = PreviewState(preview = bookmark2),
                editBookmark = EditBookmarkState(bookmark = bookmark2, topics = topics)
            ),
            newState
        )
    }
}