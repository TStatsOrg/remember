package com.app.dependencies.data.dao

import com.app.dependencies.data.dto.RealmTextBookmarkDTO
import com.app.shared.data.dao.TextBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO
import io.realm.Realm

class RealmTextBookmarkDAO(private val instance: () -> Realm): TextBookmarkDAO {

    override fun getAll(): List<BookmarkDTO.TextBookmarkDTO> {
        val realm = instance()
        val result = realm.where<RealmTextBookmarkDTO>(RealmTextBookmarkDTO::class.java).findAll()
        return realm.copyFromRealm(result).toList()
    }

    override fun insert(dto: BookmarkDTO.TextBookmarkDTO) {
        val realm = instance()
        val roomDTO = RealmTextBookmarkDTO(id = dto.id, date = dto.date, text = dto.text)
        realm.beginTransaction()
        realm.insertOrUpdate(roomDTO)
        realm.commitTransaction()
    }
}