package com.app.shared.data.dao

import com.app.shared.data.dto.BookmarkDTO

interface LinkBookmarkDAO {
    fun insert(dto: BookmarkDTO.LinkBookmarkDTO)
    fun getAll(): List<BookmarkDTO.LinkBookmarkDTO>
}