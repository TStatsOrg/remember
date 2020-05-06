package com.app.shared.feature.display

import com.app.shared.business.BookmarkState

interface DisplayViewModel {
    fun displayLink(bookmarkId: Int)

    fun observeDisplayLinkState(callback: (BookmarkState.Link) -> Unit)
}