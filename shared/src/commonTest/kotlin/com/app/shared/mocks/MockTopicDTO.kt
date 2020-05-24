package com.app.shared.mocks

import com.app.shared.data.dto.TopicDTO

data class MockTopicDTO(
    override val id: Int,
    override val name: String
) : TopicDTO