package com.app.shared.mocks

import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

sealed class MockBookmarkDTO {

    data class Image(
        override val id: Int,
        override val date: Long,
        override val topic: TopicDTO?,
        override val url: String
    ) : MockBookmarkDTO(), BookmarkDTO.ImageBookmarkDTO

    data class Text(
        override val id: Int,
        override val date: Long,
        override val topic: TopicDTO?,
        override val text: String
    ) : MockBookmarkDTO(), BookmarkDTO.TextBookmarkDTO

    data class Link(
        override val id: Int,
        override val date: Long,
        override val topic: TopicDTO?,
        override val url: String,
        override val title: String?,
        override val caption: String?,
        override val icon: String?
    ) : MockBookmarkDTO(), BookmarkDTO.LinkBookmarkDTO
}