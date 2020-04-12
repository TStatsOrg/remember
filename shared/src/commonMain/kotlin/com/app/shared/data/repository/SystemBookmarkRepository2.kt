package com.app.shared.data.repository

import com.app.shared.data.dao.ImageBookmarkDAO
import com.app.shared.data.dao.LinkBookmarkDAO
import com.app.shared.data.dao.TextBookmarkDAO
import com.app.shared.data.dto.Bookmark2DTO

class SystemBookmarkRepository2(
    private val imageBookmarkDAO: ImageBookmarkDAO,
    private val textBookmarkDAO: TextBookmarkDAO,
    private val linkBookmarkDAO: LinkBookmarkDAO
): BookmarkRepository2 {

    override fun save(dto: Bookmark2DTO) {
        when (dto) {
            is Bookmark2DTO.TextBookmarkDTO -> textBookmarkDAO.insert(dto = dto)
            is Bookmark2DTO.LinkBookmarkDTO -> linkBookmarkDAO.insert(dto = dto)
            is Bookmark2DTO.ImageBookmarkDTO -> imageBookmarkDAO.insert(dto = dto)
        }
    }

    override fun load(): List<Bookmark2DTO> {
        val texts = textBookmarkDAO.getAll()
        val links = linkBookmarkDAO.getAll()
        val images = imageBookmarkDAO.getAll()

        return (texts + links + images).sortedBy { it.date }
    }
}