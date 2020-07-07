package com.app.shared.feature.rssdetail

import com.app.shared.business.FeedDetailState

interface FeedDetailViewModel {
    fun loadFeedItems(bookmarkId: Int)
    fun subscribe(bookmarkId: Int)
    fun unsubscribe(bookmarkId: Int)
    fun observeRSSDetailsState(callback: (FeedDetailState) -> Unit)
    fun cleanup()
}