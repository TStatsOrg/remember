package com.app.shared.feature.rssdetail

import com.app.shared.business.FeedDetailState

interface RSSDetailViewModel {
    fun loadFeedItems(bookmarkId: Int)
    fun save(bookmarkId: Int)
    fun unsubscribe(bookmarkId: Int)
    fun observeRSSDetailsState(callback: (FeedDetailState) -> Unit)
    fun cleanup()
}