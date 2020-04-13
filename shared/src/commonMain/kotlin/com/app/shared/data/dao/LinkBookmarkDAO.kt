package com.app.shared.data.dao

import com.app.shared.data.dto.BookmarkDTO

interface LinkBookmarkDAO {
    suspend fun insert(dto: BookmarkDTO.LinkBookmarkDTO)
    suspend fun getAll(): List<BookmarkDTO.LinkBookmarkDTO>
}