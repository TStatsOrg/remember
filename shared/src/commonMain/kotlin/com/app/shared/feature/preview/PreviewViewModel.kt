package com.app.shared.feature.preview

import com.app.shared.business.Bookmark

interface PreviewViewModel {

    var delegate: Delegate?

    fun clear()
    fun handle(previewData: PreviewData)
    fun save(previewData: PreviewData)
    fun observePreviewState(callback: (Bookmark) -> Unit)

    interface Delegate {
        fun didSaveBookmark()
    }
}