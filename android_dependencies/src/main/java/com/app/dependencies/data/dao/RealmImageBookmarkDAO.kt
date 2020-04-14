package com.app.dependencies.data.dao

import com.app.dependencies.data.dto.RealmImageBookmarkDTO
import com.app.shared.data.dao.ImageBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO
import io.realm.Realm

class RealmImageBookmarkDAO(private val realm: Realm): ImageBookmarkDAO {

    override fun getAll(): List<BookmarkDTO.ImageBookmarkDTO> {
        val realm = Realm.getDefaultInstance()
        val result = realm.where<RealmImageBookmarkDTO>(RealmImageBookmarkDTO::class.java).findAll()
        return realm.copyFromRealm(result).toList()
    }

    override fun insert(dto: BookmarkDTO.ImageBookmarkDTO) {
        val realm = Realm.getDefaultInstance()
        val roomDTO = RealmImageBookmarkDTO(id = dto.id, date = dto.date, url = dto.url)
        realm.beginTransaction()
        realm.insertOrUpdate(roomDTO)
        realm.commitTransaction()
    }
}