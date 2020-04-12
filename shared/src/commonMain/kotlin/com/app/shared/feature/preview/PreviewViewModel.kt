package com.app.shared.feature.preview

import com.app.shared.business.Bookmark

interface PreviewViewModel {

    fun clear()
    fun handle(previewData: PreviewData)
    fun save()
    fun observePreviewState(callback: (Bookmark) -> Unit)
}