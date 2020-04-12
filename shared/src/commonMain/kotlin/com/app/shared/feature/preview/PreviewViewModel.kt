package com.app.shared.feature.preview

import com.app.shared.business.BookmarkState
import com.app.shared.data.capture.DataCapture

interface PreviewViewModel {

    var delegate: Delegate?

    fun capture(capture: DataCapture.Item)

    fun clear()
    fun handle(previewData: PreviewData)
    fun save(previewData: PreviewData)
    fun observePreviewState(callback: (BookmarkState) -> Unit)

    interface Delegate {
        fun didSaveBookmark()
    }
}