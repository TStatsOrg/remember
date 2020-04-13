package com.app.remember.tmpdependencies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.shared.data.dao.TextBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO

@Dao
interface RoomTextBookmarkDAO {

    @Insert
    suspend fun insert(dto: RoomTextBookmarkDTO)

    @Query(value = "select * from bookmarks_texts")
    suspend fun getAll(): List<RoomTextBookmarkDTO>
}

fun RoomTextBookmarkDAO.toAbstract(): TextBookmarkDAO {
    return object : TextBookmarkDAO {
        override suspend fun insert(dto: BookmarkDTO.TextBookmarkDTO) {
            this@toAbstract.insert(dto = RoomTextBookmarkDTO(
                id = dto.id,
                date = dto.date,
                text = dto.text
            ))
        }

        override suspend fun getAll(): List<BookmarkDTO.TextBookmarkDTO> {
            return this@toAbstract.getAll()
        }
    }
}