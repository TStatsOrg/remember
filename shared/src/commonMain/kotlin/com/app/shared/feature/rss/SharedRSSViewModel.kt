package com.app.shared.feature.rss

import com.app.shared.business.Actions
import com.app.shared.business.MainState
import com.app.shared.business.RSSState
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.repository.RSSRepository
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedRSSViewModel(
    private val store: Store<MainState>,
    private val repository: RSSRepository,
    private val calendarUtils: CalendarUtils
): RSSViewModel {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()

    override fun loadRSSFeeds() {
        scope.launch(context = DispatcherFactory.main()) {

            // get all RSS data items
            val rss = repository.getAll()

            // dispatch action
            store.dispatch(action = Actions.RSS.Load.Success(rss = rss, time = calendarUtils.getTime()))
        }
    }

    override fun observeRSSState(callback: (RSSState) -> Unit) {
        storeObserver
            .map { it.rss }
            .collect(callback)
    }

    override fun cleanup() {
        store.remove(observer = storeObserver)
    }
}