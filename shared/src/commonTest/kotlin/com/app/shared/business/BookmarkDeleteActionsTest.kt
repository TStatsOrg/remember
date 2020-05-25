package com.app.shared.business

import com.app.shared.DefaultTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BookmarkDeleteActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions Bookmark Add correctly`() {
        // given
        val bookmark1 = BookmarkState.Text(id = 1, text = "Text", topic = null, date = 123)
        val bookmark2 = BookmarkState.Image(id = 2, url = "https://my.cdn/image.png", topic = null, date = 123)
        val bookmark3 = BookmarkState.Link(id = 3, url = "https://my.article.com/index.html", icon = null, date = 123, title = "Title", caption = "Caption", topic = null)
        val bookmarks = listOf(bookmark1, bookmark2, bookmark3)

        val state = MainState(allBookmarks = bookmarks, bookmarks = BookmarksState(bookmarks = bookmarks))
        val action = Actions.Bookmark.Delete(bookmarkId = 3)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                allBookmarks = listOf(bookmark1, bookmark2),
                bookmarks = BookmarksState(bookmarks = listOf(bookmark1, bookmark2))
            ),
            newState
        )
    }
}