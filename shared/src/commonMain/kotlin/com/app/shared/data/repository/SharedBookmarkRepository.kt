package com.app.shared.data.repository

import com.app.shared.data.dao.BookmarkDAO
import com.app.shared.data.dto.BookmarkDTO

class SharedBookmarkRepository(
    private val dao: BookmarkDAO
): BookmarkRepository {

    override suspend fun save(bookmark: BookmarkDTO) = dao.insert(bookmark = bookmark)

    override suspend fun getAll(): List<BookmarkDTO> = dao.getAll()
}