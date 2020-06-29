package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.mocks.MockRSSDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class RSSLoadActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions RSS Load Start correctly`() {
        // given
        val state = MainState()
        val action = Actions.RSS.Load.Start(time = 123L)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(MainState(rss = RSSState()), newState)
    }

    @Test
    fun `reducer deals with Actions RSS Load Success correctly`() {
        // given
        val state = MainState()
        val dto = listOf(
            MockRSSDTO(
                id = 1,
                title = "RSS 1",
                link = "https://rss.1/feed.xml",
                caption = null,
                isSubscribed = false
            ),
            MockRSSDTO(
                id = 2,
                title = "RSS 2",
                link = "https://rss.2/feed.xml",
                caption = null,
                isSubscribed = true
            )
        )
        val action = Actions.RSS.Load.Success(time = 123L, rss = dto)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                rss = RSSState(
                    feed = listOf(
                        RSSFeedState(
                            id = 1,
                            title = "RSS 1",
                            icon = null,
                            link = "https://rss.1/feed.xml",
                            description = null,
                            isSubscribed = false
                        ),
                        RSSFeedState(
                            id = 2,
                            title = "RSS 2",
                            icon = null,
                            link = "https://rss.2/feed.xml",
                            description = null,
                            isSubscribed = true
                        )
                    ),
                    time = 123L,
                    error = null
                )
            ),
            newState)
    }

    @Test
    fun `reducer deals with Actions RSS Load Error correctly`() {
        // given
        val state = MainState()
        val error = Throwable(message = "Error")
        val action = Actions.RSS.Load.Error(time = 123L, error = error)

        // when
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(rss = RSSState(feed = listOf(), error = error, time = 123L)),
            newState)
    }
}