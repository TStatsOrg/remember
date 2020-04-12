package com.app.shared.data.repository

import com.app.shared.data.dto.Bookmark2DTO

interface BookmarkRepository {
    suspend fun save(dto: Bookmark2DTO)
    suspend fun load(): List<Bookmark2DTO>
}