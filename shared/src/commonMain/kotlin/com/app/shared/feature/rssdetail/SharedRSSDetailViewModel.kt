package com.app.shared.feature.rssdetail

import com.app.shared.business.Actions
import com.app.shared.business.Either
import com.app.shared.business.MainState
import com.app.shared.business.RSSFeedDetailState
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.data.repository.RSSFeedBookmarkRepository
import com.app.shared.data.repository.RSSRepository
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.utils.copy
import com.app.shared.utils.toDTO
import com.app.shared.utils.toState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedRSSDetailViewModel(
    private val store: Store<MainState>,
    private val feedBookmarkRepository: RSSFeedBookmarkRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val repository: RSSRepository
): RSSDetailViewModel {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()

    override fun loadFeedItems(bookmarkId: Int) {
        scope.launch(context = DispatcherFactory.main()) {

            val rss = feedBookmarkRepository.get(bookmarkId = bookmarkId)

            if (rss != null) {
                store.dispatch(action = Actions.RSS.Detail.Present(dto = rss))

                when (val result = repository.getAllItems(url = rss.url)) {
                    is Either.Success -> store.dispatch(action = Actions.RSS.Detail.LoadItems.Success(items = result.data))
                    is Either.Failure -> store.dispatch(action = Actions.RSS.Detail.LoadItems.Error(error = result.error))
                }
            }
        }
    }

    override fun save(bookmarkId: Int) {
        scope.launch(context = DispatcherFactory.main()) {

            val dto = feedBookmarkRepository.get(bookmarkId = bookmarkId)
            val newDto = dto?.toState()?.copy(withIsFavourite = true)?.toDTO()

            newDto?.let {
                bookmarkRepository.save(dto = newDto)
                store.dispatch(action = Actions.Bookmark.Favourite.Add(bookmarkId = bookmarkId))
            }
        }
    }

    override fun unsubscribe(bookmarkId: Int) {
        scope.launch(context = DispatcherFactory.main()) {

            val dto = feedBookmarkRepository.get(bookmarkId = bookmarkId)
            val newDto = dto?.toState()?.copy(withIsFavourite = false)?.toDTO()

            newDto?.let {
                bookmarkRepository.save(dto = newDto)
                store.dispatch(action = Actions.Bookmark.Favourite.Remove(bookmarkId = bookmarkId))
            }
        }
    }

    override fun observeRSSDetailsState(callback: (RSSFeedDetailState) -> Unit) {
        storeObserver
            .map { it.rssFeedDetail }
            .collect(callback)
    }

    override fun cleanup() {
        store.remove(observer = storeObserver)
    }
}