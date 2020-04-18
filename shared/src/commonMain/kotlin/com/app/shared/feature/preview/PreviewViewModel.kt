package com.app.shared.feature.preview

import com.app.shared.business.BookmarkState

interface PreviewViewModel {

    fun clear()
    fun present(capturedRawData: String?)
    fun save()

    fun observePreviewState(callback: (BookmarkState) -> Unit)
    fun observeBookmarkSaved(callback: () -> Unit)
}