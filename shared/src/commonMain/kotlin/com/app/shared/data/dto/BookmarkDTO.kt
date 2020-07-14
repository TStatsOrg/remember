package com.app.shared.data.dto

interface BookmarkDTO {
    val id: Int
    val date: Long
    val topic: TopicDTO?
    val isFavourite: Boolean

    interface TextBookmarkDTO: BookmarkDTO {
        val text: String
    }

    interface LinkBookmarkDTO: BookmarkDTO {
        val url: String
        val title: String?
        val caption: String?
        val icon: String?
    }

    interface FeedBookmarkDTO: BookmarkDTO {
        val url: String
        val title: String?
        val caption: String?
        val icon: String?
        val latestUpdate: Long
    }

    interface ImageBookmarkDTO: BookmarkDTO {
        val url: String
    }
}