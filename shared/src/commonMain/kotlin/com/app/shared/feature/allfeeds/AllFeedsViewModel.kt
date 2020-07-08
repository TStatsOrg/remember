package com.app.shared.feature.allfeeds

import com.app.shared.business.BookmarkState

interface AllFeedsViewModel {
    fun loadData()
    fun observeState(callback: (List<BookmarkState>) -> Unit)
    fun cleanup()
}