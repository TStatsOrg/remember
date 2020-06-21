package com.app.shared.feature.rssdetail

import com.app.shared.business.Actions
import com.app.shared.business.MainState
import com.app.shared.business.RSSFeedDetailState
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.repository.RSSRepository
import com.app.shared.observ.map
import com.app.shared.redux.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedRSSDetailViewModel(
    private val store: Store<MainState>,
    private val repository: RSSRepository
): RSSDetailViewModel {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()

    override fun loadRSSFeedData(rssId: Int) {
        scope.launch(context = DispatcherFactory.main()) {

            val rss = repository.get(rssId = rssId)

            if (rss != null) {
                store.dispatch(action = Actions.RSS.Detail.Present(rss = rss))

                try {
                    val items = repository.getAllItems(dto = rss)
                    store.dispatch(action = Actions.RSS.Detail.LoadItems.Success(items = items))
                } catch (e: Throwable) {
                    store.dispatch(action = Actions.RSS.Detail.LoadItems.Error(error = e))
                }
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