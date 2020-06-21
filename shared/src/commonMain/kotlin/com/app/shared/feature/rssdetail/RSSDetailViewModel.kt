package com.app.shared.feature.rssdetail

import com.app.shared.business.RSSFeedDetailState

interface RSSDetailViewModel {

    fun loadRSSFeedData(rssId: Int)
    fun observeRSSDetailsState(callback: (RSSFeedDetailState) -> Unit)
    fun cleanup()
}