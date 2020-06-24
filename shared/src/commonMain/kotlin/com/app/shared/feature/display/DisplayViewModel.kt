package com.app.shared.feature.display

import com.app.shared.business.DisplayState

interface DisplayViewModel {

    fun loadRssItem(rssItemId: Int)
    fun isItemAleradyBookmarked(rssItemId: Int): Boolean
    fun observeDisplayState(callback: (DisplayState) -> Unit)
    fun cleanup()
    fun save()
}