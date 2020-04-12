package com.app.shared.feature.mainhub

import com.app.shared.business.Bookmark

interface MainHubViewModel {

    fun loadBookmarks()
    fun observeBookmarkState(callback: (List<Bookmark>) -> Unit)
}