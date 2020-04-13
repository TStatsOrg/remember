package com.app.remember.tmpdependencies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.shared.data.dao.ImageBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO

@Dao
interface RoomImageBookmarkDAO {

    @Insert
    suspend fun insert(dto: RoomImageBookmarkDTO)

    @Query(value = "select * from bookmarks_images")
    suspend fun getAll(): List<RoomImageBookmarkDTO>
}

fun RoomImageBookmarkDAO.toAbstract(): ImageBookmarkDAO {
    return object : ImageBookmarkDAO {
        override suspend fun insert(dto: BookmarkDTO.ImageBookmarkDTO) {
            this@toAbstract.insert(dto = RoomImageBookmarkDTO(
                id = dto.id,
                url = dto.url,
                date = dto.date
            ))
        }

        override suspend fun getAll(): List<BookmarkDTO.ImageBookmarkDTO> {
            return this@toAbstract.getAll()
        }
    }
}