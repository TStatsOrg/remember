package com.app.shared.feature.bookmarks

import com.app.shared.business.BookmarksState

interface BookmarksViewModel {

    fun loadBookmarks()
    fun search(byName: String)
    fun delete(bookmarkId: Int)

    fun observeBookmarkState(callback: (BookmarksState) -> Unit)

    fun cleanup()
}