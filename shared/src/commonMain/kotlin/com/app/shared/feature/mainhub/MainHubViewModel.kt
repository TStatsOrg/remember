package com.app.shared.feature.mainhub

import com.app.shared.business.BookmarksState

interface MainHubViewModel {

    fun loadBookmarks()
    fun loadSuggestions()
    fun clearSuggestions()

    fun filterSuggestions(byName: String)
    fun filter(byTopic: String)
    fun search(byName: String)

    fun observeBookmarkState(callback: (BookmarksState) -> Unit)
}