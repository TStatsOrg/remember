package com.app.remember.tmpdependencies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.shared.data.dao.LinkBookmarkDAO
import com.app.shared.data.dto.Bookmark2DTO

@Dao
interface RoomLinkBookmarkDAO {

    @Insert
    suspend fun insert(dto: RoomLinkBookmarkDTO)

    @Query(value = "select * from bookmarks_links")
    suspend fun getAll(): List<RoomLinkBookmarkDTO>
}

fun RoomLinkBookmarkDAO.toAbstract(): LinkBookmarkDAO {
    return object : LinkBookmarkDAO {
        override suspend fun insert(dto: Bookmark2DTO.LinkBookmarkDTO) {
            this@toAbstract.insert(dto = RoomLinkBookmarkDTO(
                id = dto.id,
                date = dto.date,
                description = dto.description,
                icon = dto.icon,
                title = dto.title,
                url = dto.url
            ))
        }

        override suspend fun getAll(): List<Bookmark2DTO.LinkBookmarkDTO> {
            return this@toAbstract.getAll()
        }
    }
}