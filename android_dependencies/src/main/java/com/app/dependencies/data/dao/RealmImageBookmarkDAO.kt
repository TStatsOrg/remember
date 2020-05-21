package com.app.dependencies.data.dao

import com.app.dependencies.data.dto.RealmImageBookmarkDTO
import com.app.dependencies.data.dto.RealmTopicDTO
import com.app.shared.data.dao.ImageBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO

class RealmImageBookmarkDAO(override val instance: RealmProvider): RealmDAO, ImageBookmarkDAO {

    override fun getAll(): List<BookmarkDTO.ImageBookmarkDTO> {
        val realm = instance()
        val result = realm.where(RealmImageBookmarkDTO::class.java).findAll()
        return realm.copyFromRealm(result).toList()
    }

    override fun insert(dto: BookmarkDTO.ImageBookmarkDTO) {
        val realm = instance()

        val topicDTO = dto.topic?.let {
            RealmTopicDTO(id = it.id, name = it.name)
        }

        val roomDTO = RealmImageBookmarkDTO(id = dto.id, date = dto.date, url = dto.url, topic = topicDTO)

        realm.beginTransaction()
        realm.insertOrUpdate(roomDTO)
        realm.commitTransaction()
    }

    override fun delete(bookmarkId: Int) {
        val realm = instance()
        val result = realm.where(RealmImageBookmarkDTO::class.java).equalTo("id", bookmarkId).findAll()
        realm.beginTransaction()
        result.deleteAllFromRealm()
        realm.commitTransaction()
    }
}