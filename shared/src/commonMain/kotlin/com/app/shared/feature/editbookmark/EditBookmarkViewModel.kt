package com.app.shared.feature.editbookmark

import com.app.shared.business.EditBookmarkState

interface EditBookmarkViewModel {

    fun loadEditableBookmark(forId: Int)
    fun update(bookmark: Int, withTopic: Int)
    fun observeEditBookmarkState(callback: (EditBookmarkState) -> Unit)
    fun observeBookmarkUpdated(callback: () -> Unit)
}