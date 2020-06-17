package com.app.shared.feature.rss

import com.app.shared.business.RSSState

interface RSSViewModel {
    fun loadRSSFeeds()
    fun observeRSSState(callback: (RSSState) -> Unit)
    fun cleanup()
}