package com.app.shared.feature.mainhub

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.BookmarkState
import com.app.shared.coroutines.DefaultDispatcher
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.redux.Store
import com.app.shared.redux.asFlow
import com.app.shared.utils.CalendarUtils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SharedMainHubViewModel(
    private val store: Store<AppState>,
    private val calendar: CalendarUtils,
    private val bookmarkRepository: BookmarkRepository
): MainHubViewModel {

    private val scope = provideViewModelScope()

    override fun loadBookmarks() {
        scope.launch(context = MainDispatcher) {

            store.dispatch(action = Actions.Bookmark.Load.Start(time = calendar.getTime()))

            val dtos = bookmarkRepository.load()

            store.dispatch(action = Actions.Bookmark.Load.Success(time = calendar.getTime(), bookmarks = dtos))
        }
    }

    override fun observeBookmarkState(callback: (List<BookmarkState>) -> Unit) {
        scope.launch(context = MainDispatcher) {
            store.asFlow()
                .flowOn(DefaultDispatcher)
                .map { it.bookmarks.bookmarks }
                .collect {
                    callback(it)
                }
        }
    }
}