package com.app.shared.feature

import com.app.shared.DefaultTest
import com.app.shared.business.Actions
import com.app.shared.business.Either
import com.app.shared.business.Errors
import com.app.shared.business.MainState
import com.app.shared.coroutines.runTest
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.data.repository.FeedItemRepository
import com.app.shared.data.repository.FeedsRepository
import com.app.shared.feature.feeddetail.SharedFeedDetailViewModel
import com.app.shared.mocks.MockBookmarkDTO
import com.app.shared.mocks.MockFeedItemDTO
import com.app.shared.redux.Store
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class SharedFeedDetailViewModelTest: DefaultTest() {

    private val store = mockk<Store<MainState>>(relaxed = true)
    private val bookmarkRepository = mockk<BookmarkRepository>(relaxed = true)
    private val feedItemRepository = mockk<FeedItemRepository>(relaxed = true)
    private val feedsRepository = mockk<FeedsRepository>(relaxed = true)
    private val viewModel = SharedFeedDetailViewModel(
        store = store,
        bookmarkRepository = bookmarkRepository,
        feedItemRepository = feedItemRepository,
        feedsRepository = feedsRepository
    )

    @Test
    fun `load data to do nothing if we cannot find the corresponding bookmarked feed`() = runTest {
        // given
        coEvery { feedsRepository.get(bookmarkId = 1) } returns null

        // when
        viewModel.loadData(bookmarkId = 1)

        // then
        verify(exactly = 0) {
            store.dispatch(action = any())
        }
    }

    @Test
    fun `load data to load the current bookmarked feed as well as all associated feed items in case of success`() = runTest {
        // given
        val dto1 = MockBookmarkDTO.Feed(id = 1, title = "My feed", url = "https;//url.com/index.html", date = 123L, topic = null, caption = null, icon = null, isFavourite = true)
        val dto2 = MockFeedItemDTO(id = 1, link = "https://url.com/article1/index.html", title = "My article")

        coEvery { feedsRepository.get(bookmarkId = 1) } returns dto1
        coEvery { feedItemRepository.getAllItems(url = "https;//url.com/index.html") } returns Either.Success(data = listOf(dto2))

        // when
        viewModel.loadData(bookmarkId = 1)

        // then
        verify {
            store.dispatch(action = Actions.Feed.Detail.Present(dto = dto1))
            store.dispatch(action = Actions.Feed.Detail.LoadItems.Success(items = listOf(dto2)))
        }
    }

    @Test
    fun `view model to load the current bookmarked feed as well as dispatch an error to the store in case of failure`() = runTest {
        // given
        val dto1 = MockBookmarkDTO.Feed(id = 1, title = "My feed", url = "https;//url.com/index.html", date = 123L, topic = null, caption = null, icon = null, isFavourite = true)
        val error = Errors.Network

        coEvery { feedsRepository.get(bookmarkId = 1) } returns dto1
        coEvery { feedItemRepository.getAllItems(url = "https;//url.com/index.html") } returns Either.Failure(error = error)

        // when
        viewModel.loadData(bookmarkId = 1)

        // then
        verify {
            store.dispatch(action = Actions.Feed.Detail.Present(dto = dto1))
            store.dispatch(action = Actions.Feed.Detail.LoadItems.Error(error = error))
        }
    }

    @Test
    fun `view model to not allow subscribing to a bookmarked feed if it does not exists`() = runTest {
        // given
        coEvery { feedsRepository.get(bookmarkId = 1) } returns null

        // when
        viewModel.subscribe(bookmarkId = 1)

        // then
        coVerify(exactly = 0) {
            bookmarkRepository.save(dto = any())
            store.dispatch(action = Actions.Bookmark.Favourite.Add(bookmarkId = 1))
        }
    }

    @Test
    fun `view model to allow subscribing to a bookmarked feed if it exists`() = runTest {
        // given
        val dto1 = MockBookmarkDTO.Feed(id = 1, title = "My feed", url = "https;//url.com/index.html", date = 123L, topic = null, caption = null, icon = null, isFavourite = false)

        coEvery { feedsRepository.get(bookmarkId = 1) } returns dto1

        // when
        viewModel.subscribe(bookmarkId = 1)

        // then
        coVerify {
            bookmarkRepository.save(dto = any())
            store.dispatch(action = Actions.Bookmark.Favourite.Add(bookmarkId = 1))
        }
    }

    @Test
    fun `view model to not allow unsubscribing to a bookmarked feed if it does not exists`() = runTest {
        // given
        coEvery { feedsRepository.get(bookmarkId = 1) } returns null

        // when
        viewModel.unsubscribe(bookmarkId = 1)

        // then
        coVerify(exactly = 0) {
            bookmarkRepository.save(dto = any())
            store.dispatch(action = Actions.Bookmark.Favourite.Remove(bookmarkId = 1))
        }
    }

    @Test
    fun `view model to allow unsubscribing to a bookmarked feed if it exists`() = runTest {
        // given
        val dto1 = MockBookmarkDTO.Feed(id = 1, title = "My feed", url = "https;//url.com/index.html", date = 123L, topic = null, caption = null, icon = null, isFavourite = false)

        coEvery { feedsRepository.get(bookmarkId = 1) } returns dto1

        // when
        viewModel.unsubscribe(bookmarkId = 1)

        // then
        coVerify {
            bookmarkRepository.save(dto = any())
            store.dispatch(action = Actions.Bookmark.Favourite.Remove(bookmarkId = 1))
        }
    }

    @Test
    fun `view model to cleanup after itself`() = runTest {
        // given

        // when
        viewModel.cleanup()

        // then
        verify {
            store.remove(observer = any())
        }
    }
}