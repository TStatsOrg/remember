package com.app.remember.tmpdependencies

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RoomImageBookmarkDTO::class, RoomTextBookmarkDTO::class, RoomLinkBookmarkDTO::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun imageDao(): RoomImageBookmarkDAO
    abstract fun textDao(): RoomTextBookmarkDAO
    abstract fun linkDao(): RoomLinkBookmarkDAO
}