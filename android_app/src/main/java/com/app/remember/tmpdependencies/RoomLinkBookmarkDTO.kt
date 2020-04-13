package com.app.remember.tmpdependencies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.shared.data.dto.BookmarkDTO

@Entity(tableName = "bookmarks_links")
data class RoomLinkBookmarkDTO(
    @PrimaryKey
    override val id: Int,
    override val date: Long,
    override val url: String,
    override val title: String?,
    override val description: String?,
    override val icon: String?
) : BookmarkDTO.LinkBookmarkDTO