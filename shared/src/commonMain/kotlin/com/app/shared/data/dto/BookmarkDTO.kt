package com.app.shared.data.dto

interface BookmarkDTO {
    val id: Int
    val date: Long

    interface TextBookmarkDTO: BookmarkDTO {
        val text: String
    }

    interface LinkBookmarkDTO: BookmarkDTO {
        val url: String
        val title: String?
        val description: String?
        val icon: String?
    }

    interface ImageBookmarkDTO: BookmarkDTO {
        val url: String
    }
}