package com.app.shared.feature.feed

import com.app.shared.business.BookmarksState

interface FeedViewModel {
    fun loadData()
    fun observeBookmarkState(callback: (BookmarksState) -> Unit)
    fun cleanup()
}