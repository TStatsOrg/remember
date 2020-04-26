package com.app.dependencies.data.dao

import com.app.dependencies.data.dto.RealmTextBookmarkDTO
import com.app.dependencies.data.dto.RealmTopicDTO
import com.app.shared.data.dao.TextBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO

class RealmTextBookmarkDAO(override val instance: RealmProvider): RealmDAO, TextBookmarkDAO {

    override fun getAll(): List<BookmarkDTO.TextBookmarkDTO> {
        val realm = instance()
        val result = realm.where<RealmTextBookmarkDTO>(RealmTextBookmarkDTO::class.java).findAll()
        return realm.copyFromRealm(result).toList()
    }

    override fun insert(dto: BookmarkDTO.TextBookmarkDTO) {
        val realm = instance()

        val topicDTO = dto.topic?.let {
            RealmTopicDTO(id = it.id, name = it.name)
        }

        val roomDTO = RealmTextBookmarkDTO(id = dto.id, date = dto.date, text = dto.text, topic = topicDTO)

        realm.beginTransaction()
        realm.insertOrUpdate(roomDTO)
        realm.commitTransaction()
    }
}