package com.app.shared.data.repository

import com.app.shared.data.dto.BookmarkDTO

interface FeedsRepository {
    suspend fun loadAll(): List<BookmarkDTO>
    suspend fun get(bookmarkId: Int): BookmarkDTO.FeedBookmarkDTO?
    suspend fun getNewContent()
}