package com.app.shared.feature.capture

import com.app.shared.data.capture.DataProcess

interface DataCaptureViewModel {
    fun capture(callback: (DataProcess.Item) -> Unit)
}