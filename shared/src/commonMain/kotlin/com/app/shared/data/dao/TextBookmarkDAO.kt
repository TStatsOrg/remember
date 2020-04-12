package com.app.shared.data.dao

import com.app.shared.data.dto.Bookmark2DTO

interface TextBookmarkDAO {
    suspend fun insert(dto: Bookmark2DTO.TextBookmarkDTO)
    suspend fun getAll(): List<Bookmark2DTO.TextBookmarkDTO>
}