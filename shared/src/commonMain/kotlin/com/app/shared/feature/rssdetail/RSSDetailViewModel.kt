package com.app.shared.feature.rssdetail

import com.app.shared.business.RSSFeedDetailState

interface RSSDetailViewModel {
    fun loadFeedItems(bookmarkId: Int)
    fun save(bookmarkId: Int)
    fun unsubscribe(bookmarkId: Int)
    fun observeRSSDetailsState(callback: (RSSFeedDetailState) -> Unit)
    fun cleanup()
}