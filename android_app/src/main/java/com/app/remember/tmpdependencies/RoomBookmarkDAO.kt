package com.app.remember.tmpdependencies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoomBookmarkDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: RoomBookmarkDTO)

    @Query("Select * from bookmarks")
    suspend fun getAll(): List<RoomBookmarkDTO>
}