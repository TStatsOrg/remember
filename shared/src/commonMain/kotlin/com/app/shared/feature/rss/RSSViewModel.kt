package com.app.shared.feature.rss

import com.app.shared.business.BookmarkState

interface RSSViewModel {
    fun loadData()
    fun observeRSSState(callback: (List<BookmarkState>) -> Unit)
    fun cleanup()
}