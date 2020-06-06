package com.app.shared.feature.preview

import com.app.shared.business.PreviewState
import com.app.shared.data.capture.RawDataProcess

interface PreviewViewModel {

    fun start()
    fun present(processedData: RawDataProcess.Item)
    fun save()

    fun observePreviewState(callback: (PreviewState) -> Unit)
    fun observeBookmarkSaved(callback: (Int) -> Unit)
    fun cleanup()
}