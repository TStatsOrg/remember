package com.app.shared.feature.preview

import com.app.shared.business.PreviewState

interface PreviewViewModel {

    fun clear()
    fun present(capturedRawData: String?)
    fun save()

    fun observePreviewState(callback: (PreviewState) -> Unit)
    fun observeBookmarkSaved(callback: (Int) -> Unit)
    fun cleanup()
}