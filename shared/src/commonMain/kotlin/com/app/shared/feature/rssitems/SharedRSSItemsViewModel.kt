package com.app.shared.feature.rssitems

import com.app.shared.business.MainState
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.redux.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedRSSItemsViewModel(
    private val store: Store<MainState>
): RSSItemsViewModel {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()

    override fun loadRSSFeedData() {
        scope.launch(context = DispatcherFactory.main()) {

        }
    }
}