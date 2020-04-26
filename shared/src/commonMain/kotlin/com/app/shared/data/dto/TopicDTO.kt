package com.app.shared.data.dto

interface TopicDTO {
    val id: Int
    val name: String

    /**
     * This is the baked general topic that acts as default if no topic is added
     */
    data class GeneralTopic(override val id: Int = 0, override val name: String = "General") : TopicDTO
}