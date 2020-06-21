package com.app.shared.mocks

import com.app.shared.data.dto.RSSItemDTO

data class MockRSSItemDTO(
    override val id: Int,
    override val title: String,
    override val link: String,
    override val pubDate: String = "20th Apr 2020",
    override val caption: String? = null
) : RSSItemDTO