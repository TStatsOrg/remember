package com.app.shared.feature.preview

import com.app.shared.business.BookmarkState
import com.app.shared.data.capture.DataProcess

interface PreviewViewModel {

    var delegate: Delegate?

    fun clear()
    fun present(processed: DataProcess.Item)
    fun save(processed: DataProcess.Item)

    fun observePreviewState(callback: (BookmarkState) -> Unit)

    interface Delegate {
        fun didSaveBookmark()
    }
}