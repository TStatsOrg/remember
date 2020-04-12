package com.app.shared.feature.mainhub

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.Bookmark
import com.app.shared.coroutines.DefaultDispatcher
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.redux.Store
import com.app.shared.redux.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SharedMainHubViewModel(private val store: Store<AppState>): MainHubViewModel {

    private val scope = provideViewModelScope()

    override fun loadBookmarks() {
        scope.launch(context = MainDispatcher) {
            store.dispatch(action = Actions.Bookmark.Load(time = 1234L))
        }
    }

    override fun observeBookmarkState(callback: (List<Bookmark>) -> Unit) {
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