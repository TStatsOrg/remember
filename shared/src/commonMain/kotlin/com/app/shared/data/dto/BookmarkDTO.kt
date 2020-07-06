package com.app.shared.data.dto

interface BookmarkDTO {
    val id: Int
    val date: Long
    val topic: TopicDTO?

    interface TextBookmarkDTO: BookmarkDTO {
        val text: String
    }

    interface LinkBookmarkDTO: BookmarkDTO {
        val url: String
        val title: String?
        val caption: String?
        val icon: String?
    }

    interface RSSFeedBookmarkDTO: BookmarkDTO {
        val url: String
        val isSubscribed: Boolean
        val title: String?
        val caption: String?
        val icon: String?
    }

    interface ImageBookmarkDTO: BookmarkDTO {
        val url: String
    }
}