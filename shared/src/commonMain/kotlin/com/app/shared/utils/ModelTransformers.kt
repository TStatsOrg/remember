package com.app.shared.utils

import com.app.shared.business.BookmarkState
import com.app.shared.data.capture.RawDataProcess
import com.app.shared.data.dto.BookmarkDTO

fun RawDataProcess.Item.toDTO(date: Long): BookmarkDTO? {
    return when(this) {
        is RawDataProcess.Item.Text -> object : BookmarkDTO.TextBookmarkDTO {
            override val text: String = this@toDTO.text
            override val id: Int = this@toDTO.hashCode()
            override val date: Long = date
        }
        is RawDataProcess.Item.Link -> object : BookmarkDTO.LinkBookmarkDTO {
            override val url: String = this@toDTO.url
            override val title: String? = this@toDTO.title
            override val caption: String? = this@toDTO.description
            override val icon: String? = this@toDTO.icon
            override val id: Int = this@toDTO.url.hashCode()
            override val date: Long = date
        }
        is RawDataProcess.Item.Image -> object : BookmarkDTO.ImageBookmarkDTO {
            override val url: String = this@toDTO.url
            override val id: Int = this@toDTO.url.hashCode()
            override val date: Long = date
        }
        RawDataProcess.Item.Unknown -> null
    }
}

fun BookmarkDTO.toState(): BookmarkState {
    return when(this) {
        is BookmarkDTO.TextBookmarkDTO -> BookmarkState.Text(
            id = this.id,
            date = this.date,
            text = this.text
        )
        is BookmarkDTO.LinkBookmarkDTO -> BookmarkState.Link(
            id = this.id,
            date = this.date,
            url = this.url,
            title = this.title,
            caption = this.caption,
            icon = this.icon
        )
        is BookmarkDTO.ImageBookmarkDTO -> BookmarkState.Image(
            id = this.id,
            date = this.date,
            url = this.url
        )
        else -> BookmarkState.Unsupported(
            id = this.id,
            date = this.date
        )
    }
}

fun List<BookmarkDTO>.toState(): List<BookmarkState> = this.map { it.toState() }