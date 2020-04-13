package com.app.remember.tmpdependencies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.shared.data.dto.BookmarkDTO

@Entity(tableName = "bookmarks_images")
class RoomImageBookmarkDTO(
    @PrimaryKey
    override val id: Int,
    override val date: Long,
    override val url: String
) : BookmarkDTO.ImageBookmarkDTO