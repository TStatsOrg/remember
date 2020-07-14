package com.app.shared.mocks

import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

sealed class MockBookmarkDTO {

    data class Image(
        override val id: Int,
        override val date: Long,
        override val topic: TopicDTO?,
        override val url: String,
        override val isFavourite: Boolean = false
    ) : MockBookmarkDTO(), BookmarkDTO.ImageBookmarkDTO

    data class Text(
        override val id: Int,
        override val date: Long,
        override val topic: TopicDTO?,
        override val text: String,
        override val isFavourite: Boolean = false
    ) : MockBookmarkDTO(), BookmarkDTO.TextBookmarkDTO

    data class Link(
        override val id: Int,
        override val date: Long,
        override val topic: TopicDTO?,
        override val url: String,
        override val title: String?,
        override val caption: String?,
        override val icon: String?,
        override val isFavourite: Boolean = false
    ) : MockBookmarkDTO(), BookmarkDTO.LinkBookmarkDTO

    data class Feed(
        override val id: Int,
        override val date: Long,
        override val topic: TopicDTO?,
        override val url: String,
        override val title: String?,
        override val caption: String?,
        override val icon: String?,
        override val latestUpdate: Long = 0L,
        override val isFavourite: Boolean = false
    ) : MockBookmarkDTO(), BookmarkDTO.FeedBookmarkDTO
}