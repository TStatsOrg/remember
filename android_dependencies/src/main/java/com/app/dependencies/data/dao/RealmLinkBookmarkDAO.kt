package com.app.dependencies.data.dao

import com.app.dependencies.data.dto.RealmLinkBookmarkDTO
import com.app.shared.data.dao.LinkBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO
import io.realm.Realm

class RealmLinkBookmarkDAO(private val realm: Realm): LinkBookmarkDAO {

    override fun getAll(): List<BookmarkDTO.LinkBookmarkDTO> {
        val realm = Realm.getDefaultInstance()
        val result = realm.where<RealmLinkBookmarkDTO>(RealmLinkBookmarkDTO::class.java).findAll()
        return realm.copyFromRealm(result).toList()
    }

    override fun insert(dto: BookmarkDTO.LinkBookmarkDTO) {
        val realm = Realm.getDefaultInstance()
        val roomDTO = RealmLinkBookmarkDTO(
            id = dto.id,
            date = dto.date,
            title = dto.title,
            caption = dto.caption,
            icon = dto.icon,
            url = dto.url
        )
        realm.beginTransaction()
        realm.insertOrUpdate(roomDTO)
        realm.commitTransaction()
    }
}