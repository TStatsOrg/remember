package com.app.shared.data.dao

import com.app.shared.data.dto.Bookmark2DTO

interface ImageBookmarkDAO {
    suspend fun insert(dto: Bookmark2DTO.ImageBookmarkDTO)
    suspend fun getAll(): List<Bookmark2DTO.ImageBookmarkDTO>
}