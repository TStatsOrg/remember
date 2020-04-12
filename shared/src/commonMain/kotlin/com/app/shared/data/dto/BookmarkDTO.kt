package com.app.shared.data.dto

interface BookmarkDTO {
    val id: Int
    val content: String
    val type: Type

    enum class Type(val raw: String) {
        Text("text"),
        Link("link"),
        Unknown("n/a");

        companion object {
            fun fromString(rawValue: String): Type {
                return when (rawValue) {
                    "text" -> Text
                    "link" -> Link
                    else -> Unknown
                }
            }
        }
    }
}