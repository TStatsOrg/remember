package com.app.shared.utils

import com.app.shared.business.*
import com.app.shared.data.capture.RawDataProcess
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.FeedItemDTO
import com.app.shared.data.dto.TopicDTO

fun RawDataProcess.Item.toDTO(date: Long, topic: TopicDTO? = null): BookmarkDTO? {
    return when(this) {
        is RawDataProcess.Item.Text -> object : BookmarkDTO.TextBookmarkDTO {
            override val text: String = this@toDTO.text
            override val id: Int = this@toDTO.hashCode()
            override val date: Long = date
            override val isFavourite: Boolean = false
            override val topic: TopicDTO? = topic
        }
        is RawDataProcess.Item.Link -> object : BookmarkDTO.LinkBookmarkDTO {
            override val url: String = this@toDTO.url
            override val title: String? = this@toDTO.title
            override val caption: String? = this@toDTO.description
            override val icon: String? = this@toDTO.icon
            override val id: Int = this@toDTO.url.hashCode()
            override val date: Long = date
            override val isFavourite: Boolean = false
            override val topic: TopicDTO? = topic
        }
        is RawDataProcess.Item.Feed -> object : BookmarkDTO.RSSFeedBookmarkDTO {
            override val url: String = this@toDTO.url
            override val title: String? = this@toDTO.title
            override val caption: String? = this@toDTO.caption
            override val icon: String? = this@toDTO.icon
            override val id: Int = this@toDTO.url.hashCode()
            override val date: Long = date
            override val isFavourite: Boolean = false
            override val topic: TopicDTO? = topic
        }
        is RawDataProcess.Item.Image -> object : BookmarkDTO.ImageBookmarkDTO {
            override val url: String = this@toDTO.url
            override val id: Int = this@toDTO.url.hashCode()
            override val date: Long = date
            override val isFavourite: Boolean = false
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
            isFavourite = isFavourite,
            topic = topic?.toState()
        )
        is BookmarkDTO.LinkBookmarkDTO -> BookmarkState.Link(
            id = id,
            date = date,
            url = url,
            title = title,
            caption = caption,
            icon = icon,
            isFavourite = isFavourite,
            topic = topic?.toState()
        )
        is BookmarkDTO.ImageBookmarkDTO -> BookmarkState.Image(
            id = id,
            date = date,
            url = url,
            isFavourite = isFavourite,
            topic = topic?.toState()
        )
        is BookmarkDTO.RSSFeedBookmarkDTO -> BookmarkState.Feed(
            id = id,
            date = date,
            url = url,
            title = title,
            caption = caption,
            icon = icon,
            isFavourite = isFavourite,
            topic = topic?.toState()
        )
        else -> BookmarkState.Unsupported(
            id = id,
            date = date,
            isFavourite = isFavourite,
            topic = topic?.toState()
        )
    }
}

fun BookmarkState.toDTO(withTopic: TopicState? = null): BookmarkDTO? {
    return when (this) {
        is BookmarkState.Image -> object : BookmarkDTO.ImageBookmarkDTO {
            override val url: String = this@toDTO.url
            override val id: Int = this@toDTO.id
            override val date: Long = this@toDTO.date
            override val isFavourite: Boolean = this@toDTO.isFavourite
            override val topic: TopicDTO? = withTopic?.toDTO() ?: this@toDTO.topic?.toDTO()
        }
        is BookmarkState.Text -> object : BookmarkDTO.TextBookmarkDTO {
            override val text: String = this@toDTO.text
            override val id: Int = this@toDTO.id
            override val date: Long = this@toDTO.date
            override val isFavourite: Boolean = this@toDTO.isFavourite
            override val topic: TopicDTO? = withTopic?.toDTO() ?: this@toDTO.topic?.toDTO()
        }
        is BookmarkState.Link -> object : BookmarkDTO.LinkBookmarkDTO {
            override val url: String = this@toDTO.url
            override val title: String? = this@toDTO.title
            override val caption: String? = this@toDTO.caption
            override val icon: String? = this@toDTO.icon
            override val id: Int = this@toDTO.id
            override val date: Long = this@toDTO.date
            override val isFavourite: Boolean = this@toDTO.isFavourite
            override val topic: TopicDTO? = withTopic?.toDTO() ?: this@toDTO.topic?.toDTO()
        }
        is BookmarkState.Feed -> object : BookmarkDTO.RSSFeedBookmarkDTO {
            override val url: String = this@toDTO.url
            override val title: String? = this@toDTO.title
            override val caption: String? = this@toDTO.caption
            override val icon: String? = this@toDTO.icon
            override val id: Int = this@toDTO.id
            override val date: Long = this@toDTO.date
            override val isFavourite: Boolean = this@toDTO.isFavourite
            override val topic: TopicDTO? = withTopic?.toDTO() ?: this@toDTO.topic?.toDTO()
        }
        else -> null
    }
}

fun BookmarkDTO.RSSFeedBookmarkDTO.toState(): BookmarkState.Feed {
    return BookmarkState.Feed(
        id = this.id,
        caption = this.caption,
        title = this.title,
        topic = this.topic?.toState(),
        date = this.date,
        icon = this.icon,
        url = this.url,
        isFavourite = this.isFavourite
    )
}

fun BookmarkState.copy(withTopic: TopicState?): BookmarkState {
    return when (this) {
        is BookmarkState.Image -> BookmarkState.Image(id = id, date = date, url = url, isFavourite = isFavourite, topic = withTopic)
        is BookmarkState.Link -> BookmarkState.Link(id = id, url = url, title = title, caption = caption, icon = icon, date = date, isFavourite = isFavourite, topic = withTopic)
        is BookmarkState.Text -> BookmarkState.Text(id = id, text = text, date = date, isFavourite = isFavourite, topic = withTopic)
        is BookmarkState.Feed -> BookmarkState.Feed(id = id, title = title, url = url, caption = caption, isFavourite = isFavourite, date = date, icon = icon, topic = withTopic)
        else -> BookmarkState.Unsupported(id = id, date = date, isFavourite = isFavourite, topic = withTopic)
    }
}

fun BookmarkState.copy(withIsFavourite: Boolean): BookmarkState {
    return when (this) {
        is BookmarkState.Image -> this.copy(isFavourite = withIsFavourite)
        is BookmarkState.Feed -> this.copy(isFavourite = withIsFavourite)
        is BookmarkState.Link -> this.copy(isFavourite = withIsFavourite)
        is BookmarkState.Text -> this.copy(isFavourite = withIsFavourite)
        else -> BookmarkState.Unsupported(id = id, date = date, isFavourite = withIsFavourite, topic = topic)
    }
}

fun List<BookmarkDTO>.toBookmarkState(): List<BookmarkState> = this.map { it.toState() }

fun TopicDTO.toState(): TopicState = TopicState(id = id, name = name, isEditable = id != TopicDTO.GeneralTopic.DEFAULT_ID)

fun TopicState.toDTO(): TopicDTO = object : TopicDTO {
    override val id: Int = this@toDTO.id
    override val name: String = this@toDTO.name
}

fun List<TopicDTO>.toTopicState(): List<TopicState> = this.map { it.toState() }

fun FeedItemDTO.toState(): FeedItemState = FeedItemState(
    id = id,
    title = title,
    link = link,
    pubDate = pubDate,
    caption = caption
)

fun List<FeedItemDTO>.toFeedItemState(): List<FeedItemState> = this.map { it.toState() }