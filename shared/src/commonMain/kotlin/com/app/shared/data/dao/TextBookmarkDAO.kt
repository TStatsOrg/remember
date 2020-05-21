package com.app.shared.data.dao

import com.app.shared.data.dto.BookmarkDTO

interface TextBookmarkDAO {
    fun insert(dto: BookmarkDTO.TextBookmarkDTO)
    fun getAll(): List<BookmarkDTO.TextBookmarkDTO>
    fun delete(bookmarkId: Int)
}