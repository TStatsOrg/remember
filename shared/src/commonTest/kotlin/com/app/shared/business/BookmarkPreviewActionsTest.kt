package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.mocks.MockBookmarkDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class BookmarkPreviewActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions Bookmark Preview Start correctly`() {
        // given
        val state = MainState()
        val action = Actions.Bookmark.Preview.Start

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(MainState(
            preview = PreviewState(isLoading = true)
        ), newState)
    }

    @Test
    fun `reducer deals with Actions Bookmark Preview Present correctly`() {
        // given
        val dto = MockBookmarkDTO.Text(id = 1, text = "Text", topic = null, date = 123L)
        val state = MainState(
            preview = PreviewState(isLoading = true)
        )
        val action = Actions.Bookmark.Preview.Present(dto = dto)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(MainState(
            preview = PreviewState(
                isLoading = false,
                preview = BookmarkState.Text(
                    id = 1,
                    text = "Text",
                    topic = null,
                    date = 123L,
                    isFavourite = false
                ))
        ), newState)
    }
}