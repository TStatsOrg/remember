package com.app.remember.tmpdependencies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.shared.data.dto.BookmarkDTO

@Entity(tableName = "bookmarks")
data class RoomBookmarkDTO(
    @PrimaryKey
    override val id: Int,

    @ColumnInfo(name = "content")
    override val content: String,

    @ColumnInfo(name = "type")
    override val type: BookmarkDTO.Type
) : BookmarkDTO