package com.app.shared.data.repository

import com.app.shared.data.dto.BookmarkDTO

interface RSSFeedBookmarkRepository {
    suspend fun loadAll(): List<BookmarkDTO>
}