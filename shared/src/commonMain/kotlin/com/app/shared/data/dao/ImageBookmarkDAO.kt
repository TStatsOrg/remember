package com.app.shared.data.dao

import com.app.shared.data.dto.BookmarkDTO

interface ImageBookmarkDAO {
    suspend fun insert(dto: BookmarkDTO.ImageBookmarkDTO)
    suspend fun getAll(): List<BookmarkDTO.ImageBookmarkDTO>
}