package com.app.shared.data.dao

import com.app.shared.data.dto.Bookmark2DTO

interface LinkBookmarkDAO {
    fun insert(dto: Bookmark2DTO.LinkBookmarkDTO)
    fun getAll(): List<Bookmark2DTO.LinkBookmarkDTO>
}