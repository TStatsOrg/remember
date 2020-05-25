package com.app.shared.business

import com.app.shared.DefaultTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BookmarkUpdateActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions Bookmark Update correctly`() {
        // given
        val topic1 = TopicState(id = 998, name = "News", isEditable = true)
        val topic2 = TopicState(id = 0, name = "General", isEditable = false)
        val bookmark1 = BookmarkState.Text(id = 1, text = "Text", topic = topic1, date = 123)
        val bookmark2 = BookmarkState.Image(id = 2, url = "https://my.cdn/image.png", topic = topic2, date = 123)
        val bookmark3 = BookmarkState.Link(id = 3, url = "https://my.article.com/index.html", icon = null, date = 123, title = "Title", caption = "Caption", topic = topic1)
        val bookmarks = listOf(bookmark1, bookmark2, bookmark3)

        val state = MainState(allBookmarks = bookmarks, bookmarks = BookmarksState(bookmarks = bookmarks))

        // when
        val bookmark2Updated = bookmark2.copy(topic = topic1)
        val action = Actions.Bookmark.Update(state = bookmark2Updated)
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                allBookmarks = listOf(bookmark1, bookmark2Updated, bookmark3),
                bookmarks = BookmarksState(
                    bookmarks = listOf(bookmark1, bookmark2Updated, bookmark3)
                )
            ),
            newState
        )
    }
}