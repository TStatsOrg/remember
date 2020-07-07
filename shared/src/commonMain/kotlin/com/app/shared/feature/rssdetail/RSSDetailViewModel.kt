package com.app.shared.feature.rssdetail

import com.app.shared.business.RSSFeedDetailState

interface RSSDetailViewModel {
    fun loadFeedItems(bookmarkId: Int)
    fun subscribe(feedId: Int)
    fun unsubscribe(feedId: Int)
    fun observeRSSDetailsState(callback: (RSSFeedDetailState) -> Unit)
    fun cleanup()
}