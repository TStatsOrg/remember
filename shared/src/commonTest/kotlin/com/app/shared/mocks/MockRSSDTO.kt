package com.app.shared.mocks

import com.app.shared.data.dto.RSSDTO

data class MockRSSDTO(
    override val id: Int,
    override val title: String,
    override val icon: String? = null,
    override val link: String,
    override val caption: String?,
    override val isSubscribed: Boolean
) : RSSDTO