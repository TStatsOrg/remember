package com.app.shared.feature.feeds

import com.app.shared.business.BookmarksState

interface FeedsViewModel {
    fun loadData()
    fun checkContentUpdates()
    fun updateLatestDate(bookmarkId: Int)
    fun observeState(callback: (BookmarksState) -> Unit)
    fun cleanup()
}