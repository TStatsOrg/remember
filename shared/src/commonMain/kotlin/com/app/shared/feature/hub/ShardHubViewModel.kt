package com.app.shared.feature.hub

import com.app.shared.business.Actions
import com.app.shared.business.MainState
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import kotlinx.coroutines.launch

class ShardHubViewModel(
    private val store: Store<MainState>,
    private val calendar: CalendarUtils,
    private val bookmarkRepository: BookmarkRepository
): HubViewModel {

    private val scope = provideViewModelScope()

    override fun loadData() {
        scope.launch(context = DispatcherFactory.main()) {

            store.dispatch(action = Actions.Bookmark.Load.Start(time = calendar.getTime()))

            val dtos = bookmarkRepository.load()

            store.dispatch(action = Actions.Bookmark.Load.Success(time = calendar.getTime(), bookmarks = dtos))
        }
    }
}