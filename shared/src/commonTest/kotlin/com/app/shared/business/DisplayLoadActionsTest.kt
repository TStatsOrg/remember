package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.coroutines.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DisplayLoadActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Display Load Start actions correctly`() = runTest {
        // given
        val state = MainState()

        // when
        val action = Actions.Display.Load.Start(time = 123L)
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                display = DisplayState(isLoading = true)
            ),
            newState
        )
    }

    @Test
    fun `reducer deals with Display Load Success actions correctly with non bookmarked item`() = runTest {
        // given
        val state = MainState(display = DisplayState(isLoading = true))

        // when
        val action = Actions.Display.Load.Success(
            url = "https://my.url.com/index.html",
            caption = "This is interesting",
            time = 123L,
            title = "This is my article",
            icon = "https://my.url.com/favico.jpg"
        )
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                display = DisplayState(
                    isLoading = false,
                    isBookmarked = false,
                    item = BookmarkState.Link(
                        id = "https://my.url.com/index.html".hashCode(),
                        url = "https://my.url.com/index.html",
                        date = 123L,
                        isFavourite = false,
                        caption = "This is interesting",
                        title = "This is my article",
                        icon = "https://my.url.com/favico.jpg",
                        topic = null
                    )
                )
            ),
            newState
        )
    }

    @Test
    fun `reducer deals with Display Load Success actions correctly with bookmarked item`() = runTest {
        // given
        val bookmark1 = BookmarkState.Link(
            id = "https://my.url.com/index.html".hashCode(),
            url = "https://my.url.com/index.html",
            date = 123L,
            isFavourite = false,
            caption = "This is interesting",
            title = "This is my article",
            icon = "https://my.url.com/favico.jpg",
            topic = null
        )
        val state = MainState(
            display = DisplayState(isLoading = true),
            allBookmarks = listOf(bookmark1)
        )

        // when
        val action = Actions.Display.Load.Success(
            url = "https://my.url.com/index.html",
            caption = "This is interesting",
            time = 123L,
            title = "This is my article",
            icon = "https://my.url.com/favico.jpg"
        )
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                display = DisplayState(
                    isLoading = false,
                    isBookmarked = true,
                    item = bookmark1
                ),
                allBookmarks = listOf(bookmark1)
            ),
            newState
        )
    }

    @Test
    fun `reducer deals with Display Load Error actions correctly`() = runTest {
        // data
        val error = Throwable(message = "Some error")

        // given
        val state = MainState()

        // when
        val action = Actions.Display.Load.Error(error = error, time = 123L)
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                display = DisplayState(isLoading = false, error = error)
            ),
            newState
        )
    }
}