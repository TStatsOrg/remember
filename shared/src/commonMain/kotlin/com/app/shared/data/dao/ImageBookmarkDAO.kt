package com.app.shared.data.dao

import com.app.shared.data.dto.BookmarkDTO

interface ImageBookmarkDAO {
    fun insert(dto: BookmarkDTO.ImageBookmarkDTO)
    fun getAll(): List<BookmarkDTO.ImageBookmarkDTO>
}