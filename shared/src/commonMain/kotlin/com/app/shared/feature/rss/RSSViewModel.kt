package com.app.shared.feature.rss

import com.app.shared.business.FeedsState

interface RSSViewModel {
    fun loadRSSFeeds()
    fun observeRSSState(callback: (FeedsState) -> Unit)
    fun cleanup()
}