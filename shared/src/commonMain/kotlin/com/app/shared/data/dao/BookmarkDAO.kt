package com.app.shared.data.dao

import com.app.shared.data.dto.BookmarkDTO

interface BookmarkDAO {
    suspend fun insert(bookmark: BookmarkDTO)
    suspend fun getAll(): List<BookmarkDTO>
}