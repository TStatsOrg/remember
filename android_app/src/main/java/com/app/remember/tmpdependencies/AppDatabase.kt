package com.app.remember.tmpdependencies

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.shared.data.dao.BookmarkDAO
import com.app.shared.data.dto.BookmarkDTO

@Database(entities = [RoomBookmarkDTO::class], version = 1, exportSchema = false)
@TypeConverters(BookmarkConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun bookmarksDAO(): RoomBookmarkDAO

    fun toAbstract(): BookmarkDAO {
        return object : BookmarkDAO {
            override suspend fun insert(bookmark: BookmarkDTO) {
                return this@AppDatabase.bookmarksDAO().insert(bookmark = RoomBookmarkDTO(
                    id = bookmark.id,
                    content = bookmark.content,
                    type = bookmark.type
                ))
            }

            override suspend fun getAll(): List<BookmarkDTO> {
                return this@AppDatabase.bookmarksDAO().getAll()
            }
        }
    }
}