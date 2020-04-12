package com.app.shared.data.dao

import com.app.shared.data.dto.Bookmark2DTO

interface ImageBookmarkDAO {
    fun insert(dto: Bookmark2DTO.ImageBookmarkDTO)
    fun getAll(): List<Bookmark2DTO.ImageBookmarkDTO>
}