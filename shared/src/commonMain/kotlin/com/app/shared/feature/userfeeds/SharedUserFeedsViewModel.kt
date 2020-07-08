package com.app.shared.feature.userfeeds

import com.app.shared.business.Actions
import com.app.shared.business.BookmarksState
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

class SharedUserFeedsViewModel(
    private val store: Store<MainState>,
    private val bookmarkRepository: FeedsRepository,
    private val calendar: CalendarUtils
): UserFeedsViewModel {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()

    override fun loadData() {
        scope.launch(context = DispatcherFactory.main()) {

            store.dispatch(action = Actions.Bookmark.Load.Start(time = calendar.getTime()))

            val dtos = bookmarkRepository.loadAll()
                .filterIsInstance<BookmarkDTO.FeedBookmarkDTO>()

            store.dispatch(action = Actions.Bookmark.Load.Success(time = calendar.getTime(), bookmarks = dtos))
        }
    }

    override fun observeState(callback: (BookmarksState) -> Unit) {
        storeObserver
            .map { it.bookmarks }
            .collect(callback)
    }

    override fun cleanup() {
        store.remove(observer = storeObserver)
    }
}