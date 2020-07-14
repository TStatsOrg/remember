package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.coroutines.runTest
import com.app.shared.mocks.MockBookmarkDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class FeedDetailPresentActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Feed Detail Present action correctly`() = runTest {
        // given
        val dto = MockBookmarkDTO.Feed(
            id = 2,
            caption = "My caption",
            date = 123L,
            latestUpdate = 123L,
            icon = "https://my.feed.com/favico.jpg",
            title = "My Feed",
            url = "https://my.feed.com/feed.xml",
            topic = null,
            isFavourite = true
        )
        val state = MainState()

        // when
        val action = Actions.Feed.Detail.Present(dto = dto)
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                feedDetail = FeedDetailState(
                    feedState = BookmarkState.Feed(
                        id = 2,
                        caption = "My caption",
                        date = 123L,
                        latestUpdate = 123L,
                        icon = "https://my.feed.com/favico.jpg",
                        title = "My Feed",
                        url = "https://my.feed.com/feed.xml",
                        topic = null,
                        isFavourite = true
                    )
                )
            ),
            newState
        )
    }
}