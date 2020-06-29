package com.app.shared.feature.feed

import com.app.shared.business.RSSState

interface FeedViewModel {
    fun loadOwnRSSFeeds()
    fun observeUserRSSFeed(callback: (RSSState) -> Unit)
    fun cleanup()
}