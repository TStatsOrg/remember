package com.app.shared.data.dao

import com.app.shared.data.dto.BookmarkDTO

interface TextBookmarkDAO {
    suspend fun insert(dto: BookmarkDTO.TextBookmarkDTO)
    suspend fun getAll(): List<BookmarkDTO.TextBookmarkDTO>
}