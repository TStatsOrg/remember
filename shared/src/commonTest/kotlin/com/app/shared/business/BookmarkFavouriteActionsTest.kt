package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.coroutines.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BookmarkFavouriteActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Bookmark Favourite Add actions properly`() = runTest {
        // given
        val bookmark1 = BookmarkState.Link(id = 1, url = "https://my.article.com/index.html", icon = null, date = 123, title = "Title", caption = "Caption", topic = null, isFavourite = false)
        val bookmark2 = BookmarkState.Feed(id = 2, url = "https://my.feed.com/feed.xml", isFavourite = false, icon = null, date = 123, latestUpdate = 123, title = "My feed", topic = null, caption = null)
        val bookmarks = listOf(bookmark1, bookmark2)

        val state = MainState(allBookmarks = bookmarks, bookmarks = BookmarksState(bookmarks = bookmarks))

        // when
        val action = Actions.Bookmark.Favourite.Add(bookmarkId = 2)
        val newState = AppStateReducer(state, action)

        // then
        val bookmark1_1 = com.app.shared.business.BookmarkState.Link(id = 1, url = "https://my.article.com/index.html", icon = null, date = 123, title = "Title", caption = "Caption", topic = null, isFavourite = false)
        val bookmark2_1 = BookmarkState.Feed(id = 2, url = "https://my.feed.com/feed.xml", isFavourite = true, icon = null, date = 123, latestUpdate = 123, title = "My feed", topic = null, caption = null)

        assertEquals(
            MainState(
                allBookmarks = listOf(bookmark1_1, bookmark2_1),
                bookmarks = BookmarksState(bookmarks = listOf(bookmark1_1, bookmark2_1))
            ),
            newState
        )
    }

    @Test
    fun `reducer deals with Bookmark Favourite Remove actions properly`() = runTest {
        // given
        val bookmark1 = BookmarkState.Link(id = 1, url = "https://my.article.com/index.html", icon = null, date = 123, title = "Title", caption = "Caption", topic = null, isFavourite = false)
        val bookmark2 = BookmarkState.Feed(id = 2, url = "https://my.feed.com/feed.xml", isFavourite = true, icon = null, date = 123, latestUpdate = 123, title = "My feed", topic = null, caption = null)
        val bookmarks = listOf(bookmark1, bookmark2)

        val state = MainState(allBookmarks = bookmarks, bookmarks = BookmarksState(bookmarks = bookmarks))

        // when
        val action = Actions.Bookmark.Favourite.Remove(bookmarkId = 2)
        val newState = AppStateReducer(state, action)

        // then
        val bookmark1_1 = com.app.shared.business.BookmarkState.Link(id = 1, url = "https://my.article.com/index.html", icon = null, date = 123, title = "Title", caption = "Caption", topic = null, isFavourite = false)
        val bookmark2_1 = BookmarkState.Feed(id = 2, url = "https://my.feed.com/feed.xml", isFavourite = false, icon = null, date = 123, latestUpdate = 123L, title = "My feed", topic = null, caption = null)

        assertEquals(
            MainState(
                allBookmarks = listOf(bookmark1_1, bookmark2_1),
                bookmarks = BookmarksState(bookmarks = listOf(bookmark1_1, bookmark2_1))
            ),
            newState
        )
    }
}