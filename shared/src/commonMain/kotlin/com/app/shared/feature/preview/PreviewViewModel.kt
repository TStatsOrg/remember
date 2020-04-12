package com.app.shared.feature.preview

import com.app.shared.business.BookmarkState

interface PreviewViewModel {

    var delegate: Delegate?

    fun clear()
    fun handle(previewData: PreviewData)
    fun save(previewData: PreviewData)
    fun observePreviewState(callback: (BookmarkState) -> Unit)

    interface Delegate {
        fun didSaveBookmark()
    }
}