package com.app.shared.feature.feed

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

class SharedFeedViewModel(
    private val store: Store<MainState>,
    private val rssRepository: RSSRepository,
    private val calendarUtils: CalendarUtils
): FeedViewModel {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()

    override fun loadOwnRSSFeeds() {
        scope.launch(context = DispatcherFactory.main()) {

            val dtos = rssRepository.getUserFeeds()

            store.dispatch(action = Actions.RSS.User.Load(rss = dtos, time = calendarUtils.getTime()))
        }
    }

    override fun observeUserRSSFeed(callback: (RSSState) -> Unit) {
        storeObserver
            .map { it.userRssFeeds }
            .collect(callback)
    }

    override fun cleanup() {
        store.remove(observer = storeObserver)
    }
}