package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.mocks.MockBookmarkDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class BookmarkAddActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions Bookmark Add correctly`() {
        // given
        val bookmark1 = BookmarkState.Text(id = 1, text = "Text", topic = null, date = 123, isFavourite = false)
        val bookmark2 = BookmarkState.Image(id = 2, url = "https://my.cdn/image.png", topic = null, date = 123, isFavourite = false)
        val bookmarks = listOf(bookmark1, bookmark2)

        val newDto = MockBookmarkDTO.Link(id = 3, url = "https://my.article.com/index.html", date = 123, title = "Title", caption = "Caption", icon = null, topic = null, isFavourite = false)

        val state = MainState(allBookmarks = bookmarks, bookmarks = BookmarksState(bookmarks = bookmarks))
        val action = Actions.Bookmark.Add(dto = newDto)

        // when
        val newState = AppStateReducer(state, action)

        // then
        val addedBookmark = BookmarkState.Link(
            id = 3,
            url = "https://my.article.com/index.html",
            icon = null,
            date = 123,
            title = "Title",
            caption = "Caption",
            topic = null,
            isFavourite = false
        )
        val newBookmarks = listOf(
            addedBookmark,
            bookmark1,
            bookmark2
        )
        assertEquals(
            MainState(
                allBookmarks = newBookmarks,
                bookmarks = BookmarksState(bookmarks = newBookmarks)
            ),
            newState
        )
    }
}