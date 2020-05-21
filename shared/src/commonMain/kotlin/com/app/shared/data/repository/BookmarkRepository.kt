package com.app.shared.data.repository

import com.app.shared.data.dto.BookmarkDTO

interface BookmarkRepository {
    suspend fun save(dto: BookmarkDTO)
    suspend fun load(): List<BookmarkDTO>
    suspend fun delete(bookmarkId: Int)
}