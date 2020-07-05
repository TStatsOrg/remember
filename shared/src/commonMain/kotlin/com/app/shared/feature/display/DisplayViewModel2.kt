package com.app.shared.feature.display

import com.app.shared.business.DisplayState

interface DisplayViewModel2 {
    fun startLoad()
    fun finishLoad(url: String, content: String)
    fun save()
    fun delete()

    fun observeDisplayState(callback: (DisplayState) -> Unit)
    fun cleanup()
}