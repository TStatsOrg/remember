package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.mocks.MockBookmarkDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class BookmarkSaveActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions Bookmark Bookmark Reset correctly`() {
        // given
        val bookmark1 = BookmarkState.Text(id = 1, text = "Text", topic = null, date = 123)
        val bookmark2 = BookmarkState.Image(id = 2, url = "https://my.cdn/image.png", topic = null, date = 123)
        val bookmarks = listOf(bookmark1, bookmark2)

        val newDto = MockBookmarkDTO.Link(id = 3, url = "https://my.article.com/index.html", date = 123, title = "Title", caption = "Caption", icon = null, topic = null)

        val state = MainState(bookmarks = BookmarksState(bookmarks = bookmarks))
        val action = Actions.Bookmark.Save(dto = newDto)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                bookmarks = BookmarksState(
                    bookmarks = listOf(
                        BookmarkState.Link(
                            id = 3,
                            url = "https://my.article.com/index.html",
                            icon = null,
                            date = 123,
                            title = "Title",
                            caption = "Caption",
                            topic = null
                        ),
                        bookmark1,
                        bookmark2
                    )
                )
            ),
            newState
        )
    }
}