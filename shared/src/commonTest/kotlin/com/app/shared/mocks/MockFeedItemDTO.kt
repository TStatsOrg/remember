package com.app.shared.mocks

import com.app.shared.data.dto.FeedItemDTO

data class MockFeedItemDTO(
    override val id: Int,
    override val title: String,
    override val link: String,
    override val pubDate: Long = 0L,
    override val caption: String? = null
) : FeedItemDTO