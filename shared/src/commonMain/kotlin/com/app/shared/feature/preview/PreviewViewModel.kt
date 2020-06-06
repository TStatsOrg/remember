package com.app.shared.feature.preview

import com.app.shared.business.PreviewState

interface PreviewViewModel<Input> {

    fun capture(input: Input)
    fun save()

    fun observePreviewState(callback: (PreviewState) -> Unit)
    fun observeBookmarkSaved(callback: (Int) -> Unit)
    fun cleanup()
}