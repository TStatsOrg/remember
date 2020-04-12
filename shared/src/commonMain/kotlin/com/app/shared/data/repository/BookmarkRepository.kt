package com.app.shared.data.repository

import com.app.shared.data.dto.BookmarkDTO

interface BookmarkRepository {
    suspend fun save(bookmark: BookmarkDTO)
    suspend fun getAll(): List<BookmarkDTO>
}