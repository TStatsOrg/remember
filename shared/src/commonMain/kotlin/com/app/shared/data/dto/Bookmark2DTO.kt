package com.app.shared.data.dto

interface Bookmark2DTO {
    val id: Int
    val date: Long

    interface TextBookmarkDTO: Bookmark2DTO {
        val text: String
    }

    interface LinkBookmarkDTO: Bookmark2DTO {
        val url: String
        val title: String?
        val description: String?
        val icon: String?
    }

    interface ImageBookmarkDTO: Bookmark2DTO {
        val url: String
    }
}