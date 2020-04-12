package com.app.shared.utils

import com.app.shared.business.BookmarkState2
import com.app.shared.data.capture.DataProcess
import com.app.shared.data.dto.Bookmark2DTO

fun DataProcess.Item.toDTO(date: Long): Bookmark2DTO? {
    return when(this) {
        is DataProcess.Item.Text -> object : Bookmark2DTO.TextBookmarkDTO {
            override val text: String = this@toDTO.text
            override val id: Int = this@toDTO.hashCode()
            override val date: Long = date
        }
        is DataProcess.Item.Link -> object : Bookmark2DTO.LinkBookmarkDTO {
            override val url: String = this@toDTO.url
            override val title: String? = this@toDTO.title
            override val description: String? = this@toDTO.description
            override val icon: String? = this@toDTO.icon
            override val id: Int = this@toDTO.url.hashCode()
            override val date: Long = date
        }
        is DataProcess.Item.Image -> object : Bookmark2DTO.ImageBookmarkDTO {
            override val url: String = this@toDTO.url
            override val id: Int = this@toDTO.url.hashCode()
            override val date: Long = date
        }
        DataProcess.Item.Unknown -> null
    }
}

fun Bookmark2DTO.toState(): BookmarkState2 {
    return when(this) {
        is Bookmark2DTO.TextBookmarkDTO -> BookmarkState2.Text(
            id = this.id,
            date = this.date,
            text = this.text
        )
        is Bookmark2DTO.LinkBookmarkDTO -> BookmarkState2.Link(
            id = this.id,
            date = this.date,
            url = this.url,
            title = this.title,
            description = this.description,
            icon = this.icon
        )
        is Bookmark2DTO.ImageBookmarkDTO -> BookmarkState2.Image(
            id = this.id,
            date = this.date,
            url = this.url
        )
        else -> BookmarkState2.Unsupported(
            id = this.id,
            date = this.date
        )
    }
}

fun List<Bookmark2DTO>.toState(): List<BookmarkState2> = this.map { it.toState() }