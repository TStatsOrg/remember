package com.app.remember.tmpdependencies

import androidx.room.TypeConverter
import com.app.shared.data.dto.BookmarkDTO

class BookmarkConverters {

    @TypeConverter
    fun typeToString(value: BookmarkDTO.Type): String = value.raw

    @TypeConverter
    fun stringToType(value: String): BookmarkDTO.Type = BookmarkDTO.Type.fromString(rawValue = value)
}