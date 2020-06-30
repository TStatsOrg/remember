package com.app.shared.feature.display

import com.app.shared.business.DisplayState

interface DisplayViewModel {

    fun loadDisplayItem(itemId: Int)
    fun observeDisplayState(callback: (DisplayState) -> Unit)
    fun cleanup()
    fun save()
    fun delete()
}