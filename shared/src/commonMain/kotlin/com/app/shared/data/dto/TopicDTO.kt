package com.app.shared.data.dto

interface TopicDTO {
    val id: Int
    val name: String

    /**
     * This is the baked general topic that acts as default if no topic is added
     */
    data class GeneralTopic(
        override val id: Int = DEFAULT_ID,
        override val name: String = "General"
    ): TopicDTO {
        companion object {
            const val DEFAULT_ID = 0
        }
    }
}