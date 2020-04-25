package com.app.shared.utils

import com.app.shared.business.BookmarkState
import com.app.shared.business.TopicState
import com.app.shared.data.capture.RawDataProcess
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

fun RawDataProcess.Item.toDTO(date: Long, topic: TopicDTO? = null): BookmarkDTO? {
    return when(this) {
        is RawDataProcess.Item.Text -> object : BookmarkDTO.TextBookmarkDTO {
            override val text: String = this@toDTO.text
            override val id: Int = this@toDTO.hashCode()
            override val date: Long = date
            override val topic: TopicDTO? = topic
        }
        is RawDataProcess.Item.Link -> object : BookmarkDTO.LinkBookmarkDTO {
            override val url: String = this@toDTO.url
            override val title: String? = this@toDTO.title
            override val caption: String? = this@toDTO.description
            override val icon: String? = this@toDTO.icon
            override val id: Int = this@toDTO.url.hashCode()
            override val date: Long = date
            override val topic: TopicDTO? = topic
        }
        is RawDataProcess.Item.Image -> object : BookmarkDTO.ImageBookmarkDTO {
            override val url: String = this@toDTO.url
            override val id: Int = this@toDTO.url.hashCode()
            override val date: Long = date
            override val topic: TopicDTO? = topic
        }
        RawDataProcess.Item.Unknown -> null
    }
}

fun BookmarkDTO.toState(): BookmarkState {
    return when(this) {
        is BookmarkDTO.TextBookmarkDTO -> BookmarkState.Text(
            id = id,
            date = date,
            text = text,
            topic = topic?.toState()
        )
        is BookmarkDTO.LinkBookmarkDTO -> BookmarkState.Link(
            id = id,
            date = date,
            url = url,
            title = title,
            caption = caption,
            icon = icon,
            topic = topic?.toState()
        )
        is BookmarkDTO.ImageBookmarkDTO -> BookmarkState.Image(
            id = id,
            date = date,
            url = url,
            topic = topic?.toState()
        )
        else -> BookmarkState.Unsupported(
            id = id,
            date = date,
            topic = topic?.toState()
        )
    }
}

fun List<BookmarkDTO>.toBookmarkState(): List<BookmarkState> = this.map { it.toState() }

fun TopicDTO.toState(): TopicState = TopicState(id = id, name = name)

fun List<TopicDTO>.toTopicState(): List<TopicState> = this.map { it.toState() }