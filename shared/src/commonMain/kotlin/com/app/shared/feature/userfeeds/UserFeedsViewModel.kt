package com.app.shared.feature.userfeeds

import com.app.shared.business.BookmarksState

interface UserFeedsViewModel {
    fun loadData()
    fun observeState(callback: (BookmarksState) -> Unit)
    fun cleanup()
}