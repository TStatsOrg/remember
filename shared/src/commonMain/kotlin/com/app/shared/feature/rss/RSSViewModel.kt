package com.app.shared.feature.rss

import com.app.shared.business.BookmarkState

interface RSSViewModel {
    fun loadRSSFeeds()
    fun observeRSSState(callback: (List<BookmarkState>) -> Unit)
    fun cleanup()
}