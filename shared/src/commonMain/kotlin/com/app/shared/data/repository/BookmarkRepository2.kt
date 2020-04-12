package com.app.shared.data.repository

import com.app.shared.data.dto.Bookmark2DTO

interface BookmarkRepository2 {
    fun save(dto: Bookmark2DTO)
    fun load(): List<Bookmark2DTO>
}