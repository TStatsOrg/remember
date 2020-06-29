package com.app.shared.feature.rssdetail

import com.app.shared.business.Actions
import com.app.shared.business.Either
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

                when (val result = repository.getAllItems(dto = rss)) {
                    is Either.Success -> store.dispatch(action = Actions.RSS.Detail.LoadItems.Success(items = result.data))
                    is Either.Failure -> store.dispatch(action = Actions.RSS.Detail.LoadItems.Error(error = result.error))
                }
            }
        }
    }

    override fun subscribe(feedId: Int) {
        scope.launch(context = DispatcherFactory.main()) {

            // subscribe
            repository.subscribe(rssId = feedId)

            // dispatch action
            store.dispatch(action = Actions.RSS.Subscribe(id = feedId))
        }
    }

    override fun unsubscribe(feedId: Int) {
        scope.launch(context = DispatcherFactory.main()) {

            // unsubscribe
            repository.unsubscribe(rssId = feedId)

            // dispatch action
            store.dispatch(action = Actions.RSS.Unsubscribe(id = feedId))
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