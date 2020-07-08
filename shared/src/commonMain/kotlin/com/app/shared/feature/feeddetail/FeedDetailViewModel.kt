package com.app.shared.feature.feeddetail

import com.app.shared.business.FeedDetailState

interface FeedDetailViewModel {
    fun loadFeedItems(bookmarkId: Int)
    fun subscribe(bookmarkId: Int)
    fun unsubscribe(bookmarkId: Int)
    fun observeState(callback: (FeedDetailState) -> Unit)
    fun cleanup()
}