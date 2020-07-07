package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.mocks.MockRSSDTO
import com.app.shared.mocks.MockRSSItemDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class RSSDetailActionsTest: DefaultTest() {

    @Test
    fun `reducer deals with Actions RSS Detail Present correctly`() {
        // data
        val rss = MockRSSDTO(
            id = 1,
            title = "My RSS Feed",
            icon = null,
            caption = null,
            isSubscribed = false,
            link = "https://my.rss1/feed.xml"
        )

        // given
        val main = MainState()
        val action = Actions.RSS.Detail.Present(dto = rss)

        // when
        val result = AppStateReducer(main, action)

        // then
        assertEquals(
            MainState(
                rssFeedDetail = RSSFeedDetailState(
                    feedState = RSSFeedState(
                        id = 1,
                        title = "My RSS Feed",
                        description = null,
                        isSubscribed = false,
                        link = "https://my.rss1/feed.xml"
                    ),
                    items = listOf(),
                    error = null
                )
            ),
            result
        )
    }

        @Test
    fun `reducer deals with Actions RSS Detail Load Items Start correctly`() {
            // given
            val main = MainState(
                rssFeedDetail = RSSFeedDetailState(
                    feedState = RSSFeedState(
                        id = 1,
                        title = "My RSS Feed",
                        description = null,
                        isSubscribed = false,
                        link = "https://my.rss1/feed.xml"
                    ),
                    items = listOf(
                        RSSFeedItemState(
                            id = 5,
                            title = "Item 5",
                            caption = null,
                            link = "https://my.rss.item.4/index.html"
                        )
                    ),
                    error = null
                )
            )

            // when
            val action = Actions.RSS.Detail.LoadItems.Start
            val result = AppStateReducer(main, action)

            // then
            assertEquals(
                MainState(
                    rssFeedDetail = RSSFeedDetailState(
                        feedState = RSSFeedState(
                            id = 1,
                            title = "My RSS Feed",
                            description = null,
                            isSubscribed = false,
                            link = "https://my.rss1/feed.xml"
                        ),
                        items = listOf(),
                        error = null
                    )
                ),
                result
            )
        }

    @Test
    fun `reducer deals with Actions RSS Detail Load Items Success correctly`() {
        // data
        val item1 = MockRSSItemDTO(id = 1, title = "Item 1", link = "https://my.rss.item.1/index.html")
        val item2 = MockRSSItemDTO(id = 2, title = "Item 2", link = "https://my.rss.item.2/index.html")
        val item3 = MockRSSItemDTO(id = 3, title = "Item 3", link = "https://my.rss.item.3/index.html")

        // given
        val main = MainState(
            rssFeedDetail = RSSFeedDetailState(
                feedState = RSSFeedState(
                    id = 1,
                    title = "My RSS Feed",
                    description = null,
                    isSubscribed = false,
                    link = "https://my.rss1/feed.xml"
                ),
                items = listOf(),
                error = Errors.InvalidRSSFormat
            )
        )

        // when
        val action = Actions.RSS.Detail.LoadItems.Success(items = listOf(item1, item2, item3))
        val result = AppStateReducer(main, action)

        // then
        assertEquals(
            MainState(
                rssFeedDetail = RSSFeedDetailState(
                    feedState = RSSFeedState(
                        id = 1,
                        title = "My RSS Feed",
                        description = null,
                        isSubscribed = false,
                        link = "https://my.rss1/feed.xml"
                    ),
                    items = listOf(
                        RSSFeedItemState(
                            id = 1,
                            title = "Item 1",
                            caption = null,
                            pubDate = 0L,
                            link = "https://my.rss.item.1/index.html"
                        ),
                        RSSFeedItemState(
                            id = 2,
                            title = "Item 2",
                            caption = null,
                            pubDate = 0L,
                            link = "https://my.rss.item.2/index.html"
                        ),
                        RSSFeedItemState(
                            id = 3,
                            title = "Item 3",
                            caption = null,
                            pubDate = 0L,
                            link = "https://my.rss.item.3/index.html"
                        )
                    ),
                    error = null
                )
            ),
            result
        )
    }

    @Test
    fun `reducer deals with Actions RSS Detail Load Items Error correctly`() {
        // data
        val error = Throwable(message = "Network error")

        // given
        val main = MainState(
            rssFeedDetail = RSSFeedDetailState(
                feedState = RSSFeedState(
                    id = 1,
                    title = "My RSS Feed",
                    description = null,
                    isSubscribed = false,
                    link = "https://my.rss1/feed.xml"
                ),
                items = listOf(
                    RSSFeedItemState(
                        id = 1,
                        title = "Item 1",
                        caption = null,
                        pubDate = 0L,
                        link = "https://my.rss.item.1/index.html"
                    )
                ),
                error = null
            )
        )

        // when
        val action = Actions.RSS.Detail.LoadItems.Error(error = error)
        val result = AppStateReducer(main, action)

        // then
        assertEquals(
            MainState(
                rssFeedDetail = RSSFeedDetailState(
                    feedState = RSSFeedState(
                        id = 1,
                        title = "My RSS Feed",
                        description = null,
                        isSubscribed = false,
                        link = "https://my.rss1/feed.xml"
                    ),
                    items = listOf(),
                    error = error
                )
            ),
            result
        )
    }
}