package com.app.shared.feature.allfeeds

import com.app.shared.business.Actions
import com.app.shared.business.BookmarkState
import com.app.shared.business.MainState
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.repository.FeedsRepository
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedAllFeedsViewModel(
    private val store: Store<MainState>,
    private val repository: FeedsRepository,
    private val calendarUtils: CalendarUtils
): AllFeedsViewModel {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()

    override fun loadData() {
        scope.launch(context = DispatcherFactory.main()) {

            // dispatch initial action
            store.dispatch(action = Actions.Bookmark.Load.Start(time = calendarUtils.getTime()))

            // get all Bookmarked RSS items, users and default ones
            val rss = repository.loadAll().filterIsInstance<BookmarkDTO.RSSFeedBookmarkDTO>()

            // dispatch final action
            store.dispatch(action = Actions.Bookmark.Load.Success(time = calendarUtils.getTime(), bookmarks = rss))
        }
    }

    override fun observeState(callback: (List<BookmarkState>) -> Unit) {
        storeObserver
            .map { it.allBookmarks }
            .collect(callback)
    }

    override fun cleanup() {
        store.remove(observer = storeObserver)
    }
}