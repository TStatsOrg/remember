package com.app.shared.business

import com.app.shared.DefaultTest
import com.app.shared.coroutines.runTest
import com.app.shared.mocks.MockFeedItemDTO
import kotlin.test.Test
import kotlin.test.assertEquals

class FeedDetailLoadItemsTest: DefaultTest() {

    @Test
    fun `reducer deals with Feed Detail Load Items Start action correctly`() = runTest {
        // given
        val feedItem1 = FeedItemState(
            id = 10,
            title = "My item",
            link = "https://my.item.com/index.html",
            pubDate = 123L,
            caption = null
        )
        val items = listOf(feedItem1)
        val feedState = BookmarkState.Feed(
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
        val state = MainState(
            feedDetail = FeedDetailState(
                feedState = feedState,
                items = items
            )
        )

        // when
        val action = Actions.Feed.Detail.LoadItems.Start
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                feedDetail = FeedDetailState(
                    feedState = feedState,
                    items = listOf()
                )
            ),
            newState
        )
    }

    @Test
    fun `reducer deals with Feed Detail Load Items Success action correctly`() = runTest {
        // given
        val feedState = BookmarkState.Feed(
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
        val state = MainState(
            feedDetail = FeedDetailState(
                feedState = feedState,
                items = listOf()
            )
        )

        // when
        val dto1 = MockFeedItemDTO(id = 10, link = "https://my.item1.com/index.html", title = "My item 1")
        val dto2 = MockFeedItemDTO(id = 11, link = "https://my.item2.com/index.html", title = "My item 2")
        val action = Actions.Feed.Detail.LoadItems.Success(items = listOf(dto1, dto2))

        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                feedDetail = FeedDetailState(
                    feedState = feedState,
                    items = listOf(
                        FeedItemState(
                            id = 10,
                            title = "My item 1",
                            link = "https://my.item1.com/index.html",
                            pubDate = 0L,
                            caption = null
                        ),
                        FeedItemState(
                            id = 11,
                            title = "My item 2",
                            link = "https://my.item2.com/index.html",
                            pubDate = 0L,
                            caption = null
                        )
                    )
                )
            ),
            newState
        )
    }

    @Test
    fun `reducer deals with Feed Detail Load Items Error action correctly`() = runTest {
        // given
        val error = Throwable(message = "Error")
        val feedItem1 = FeedItemState(
            id = 10,
            title = "My item",
            link = "https://my.item.com/index.html",
            pubDate = 123L,
            caption = null
        )
        val items = listOf(feedItem1)
        val feedState = BookmarkState.Feed(
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
        val state = MainState(
            feedDetail = FeedDetailState(
                feedState = feedState,
                items = items
            )
        )

        // when
        val action = Actions.Feed.Detail.LoadItems.Error(error = error)
        val newState = AppStateReducer(state, action)

        // then
        assertEquals(
            MainState(
                feedDetail = FeedDetailState(
                    feedState = feedState,
                    items = listOf(),
                    error = error
                )
            ),
            newState
        )
    }
}