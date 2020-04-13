package com.app.shared.data.repository

import com.app.shared.data.dao.ImageBookmarkDAO
import com.app.shared.data.dao.LinkBookmarkDAO
import com.app.shared.data.dao.TextBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO

class SystemBookmarkRepository(
    private val imageBookmarkDAO: ImageBookmarkDAO,
    private val textBookmarkDAO: TextBookmarkDAO,
    private val linkBookmarkDAO: LinkBookmarkDAO
): BookmarkRepository {

    override suspend fun save(dto: BookmarkDTO) {
        when (dto) {
            is BookmarkDTO.TextBookmarkDTO -> textBookmarkDAO.insert(dto = dto)
            is BookmarkDTO.LinkBookmarkDTO -> linkBookmarkDAO.insert(dto = dto)
            is BookmarkDTO.ImageBookmarkDTO -> imageBookmarkDAO.insert(dto = dto)
        }
    }

    override suspend fun load(): List<BookmarkDTO> {
        val texts = textBookmarkDAO.getAll()
        val links = linkBookmarkDAO.getAll()
        val images = imageBookmarkDAO.getAll()

        return (texts + links + images).sortedBy { it.date }
    }
}