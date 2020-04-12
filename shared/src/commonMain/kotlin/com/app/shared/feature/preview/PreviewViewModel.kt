package com.app.shared.feature.preview

import com.app.shared.business.BookmarkState
import com.app.shared.data.capture.DataCapture

interface PreviewViewModel {

    var delegate: Delegate?

    fun clear()
    fun capture(capture: DataCapture)
    fun save(capture: DataCapture)

    fun observePreviewState(callback: (BookmarkState) -> Unit)

    interface Delegate {
        fun didSaveBookmark()
    }
}