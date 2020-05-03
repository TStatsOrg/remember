package com.app.shared.feature.mainhub

import com.app.shared.business.BookmarkState

interface MainHubViewModel {

    fun loadBookmarks()
    fun filter(byTopic: String)
    fun search(byName: String)
    fun observeBookmarkState(callback: (List<BookmarkState>) -> Unit)
}