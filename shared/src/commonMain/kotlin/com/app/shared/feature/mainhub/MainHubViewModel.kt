package com.app.shared.feature.mainhub

import com.app.shared.business.BookmarkState

interface MainHubViewModel {

    fun loadBookmarks()
    fun observeBookmarkState(callback: (List<BookmarkState>) -> Unit)
}