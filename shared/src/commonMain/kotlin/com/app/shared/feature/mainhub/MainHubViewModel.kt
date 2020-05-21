package com.app.shared.feature.mainhub

import com.app.shared.business.BookmarksState

interface MainHubViewModel {

    fun loadBookmarks()
    fun search(byName: String)
    fun delete(bookmarkId: Int)

    fun observeBookmarkState(callback: (BookmarksState) -> Unit)

    fun cleanup()
}