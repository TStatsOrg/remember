package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.mocks.MockBookmarkDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class BookmarkLoadActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions Bookmark Load Start correctly`() {
        // given
        val state = MainState()
        val action = Actions.Bookmark.Load.Start(time = 123L)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(MainState(
            bookmarks = BookmarksState(date = 123L)
        ), newState)
    }

    @Test
    fun `reducer deals with Actions Bookmark Load Success correctly`() {
        // given
        val bookmark1 = MockBookmarkDTO.Text(id = 1, text = "Text", topic = null, date = 123)
        val bookmark2 = MockBookmarkDTO.Image(id = 2, url = "https://my.cdn/image.png", topic = null, date = 123)
        val bookmarks = listOf<BookmarkDTO>(bookmark1, bookmark2)
        val state = MainState()
        val action = Actions.Bookmark.Load.Success(time = 123L, bookmarks = bookmarks)

        // when
        val newState = AppStateReducer(state, action)

        // then
        val newList = listOf(
            BookmarkState.Text(
                id = 1,
                text = "Text",
                topic = null,
                date = 123
            ),
            BookmarkState.Image(
                id = 2,
                url = "https://my.cdn/image.png",
                topic = null,
                date = 123
            )
        )
        assertEquals(MainState(
            allBookmarks = newList,
            bookmarks = BookmarksState(
                date = 123L,
                bookmarks = newList)
        ), newState)
    }

    @Test
    fun `reducer deals with Actions Bookmark Load Error correctly`() {
        // given
        val error = Throwable(message = "Error")
        val state = MainState()
        val action = Actions.Bookmark.Load.Error(time = 123L, error = error)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(MainState(
            bookmarks = BookmarksState(
                date = 123L,
                error = error)
        ), newState)
    }
}