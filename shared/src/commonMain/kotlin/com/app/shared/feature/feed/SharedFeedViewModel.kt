package com.app.shared.feature.feed

import com.app.shared.business.Actions
import com.app.shared.business.BookmarksState
import com.app.shared.business.MainState
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedFeedViewModel(
    private val store: Store<MainState>,
    private val bookmarkRepository: BookmarkRepository,
    private val calendar: CalendarUtils
): FeedViewModel {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()

    override fun loadBookmarkedRSSFeeds() {
        scope.launch(context = DispatcherFactory.main()) {

            store.dispatch(action = Actions.Bookmark.Load.Start(time = calendar.getTime()))

            val dtos = bookmarkRepository.load().filterIsInstance<BookmarkDTO.RSSFeedBookmarkDTO>()

            store.dispatch(action = Actions.Bookmark.Load.Success(time = calendar.getTime(), bookmarks = dtos))
        }
    }

    override fun observeBookmarkState(callback: (BookmarksState) -> Unit) {
        storeObserver
            .map { it.bookmarks }
            .collect(callback)
    }

    override fun cleanup() {
        store.remove(observer = storeObserver)
    }
}