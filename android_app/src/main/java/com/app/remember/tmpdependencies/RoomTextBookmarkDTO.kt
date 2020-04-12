package com.app.remember.tmpdependencies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.shared.data.dto.Bookmark2DTO

@Entity(tableName = "bookmarks_texts")
class RoomTextBookmarkDTO(
    @PrimaryKey
    override val id: Int,
    override val date: Long,
    override val text: String
) : Bookmark2DTO.TextBookmarkDTO