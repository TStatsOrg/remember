package com.app.shared.business

import com.app.shared.DefaultTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BookmarkSearchActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions Bookmark Filter correctly`() {
        // given
        val topic1 = TopicState(id = 998, name = "News", isEditable = true)
        val topic2 = TopicState(id = 0, name = "General", isEditable = false)
        val bookmark1 = BookmarkState.Text(id = 1, text = "Text", topic = topic1, date = 123, isFavourite = false)
        val bookmark2 = BookmarkState.Image(id = 2, url = "https://my.cdn/image.png", topic = topic2, date = 123, isFavourite = false)
        val bookmark3 = BookmarkState.Link(id = 3, url = "https://my.article.com/index.html", icon = null, date = 123, title = "General News", caption = "Caption", isFavourite = false, topic = topic1)
        val bookmarks = listOf(bookmark1, bookmark2, bookmark3)

        val state = MainState(allBookmarks = bookmarks, bookmarks = BookmarksState(bookmarks = bookmarks))

        // when
        val action1 = Actions.Bookmark.Search(term = "gen")
        val newState1 = AppStateReducer(state, action1)

        // then
        assertEquals(
            MainState(
                allBookmarks = listOf(bookmark1, bookmark2, bookmark3),
                bookmarks = BookmarksState(
                    bookmarks = listOf(bookmark2, bookmark3),
                    filterByTopic = null,
                    searchTerm = "gen"
                )
            ),
            newState1
        )

        // when
        val action2 = Actions.Bookmark.Search(term = "text")
        val newState2 = AppStateReducer(newState1, action2)

        // then
        assertEquals(
            MainState(
                allBookmarks = listOf(bookmark1, bookmark2, bookmark3),
                bookmarks = BookmarksState(
                    bookmarks = listOf(bookmark1),
                    filterByTopic = null,
                    searchTerm = "text")
            ),
            newState2
        )
    }
}