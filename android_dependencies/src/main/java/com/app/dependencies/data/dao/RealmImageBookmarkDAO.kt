package com.app.dependencies.data.dao

import com.app.dependencies.data.dto.RealmImageBookmarkDTO
import com.app.dependencies.data.dto.RealmTopicDTO
import com.app.shared.data.dao.ImageBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

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

    override fun replaceTopic(topicId: Int, withTopicDTO: TopicDTO) {
        val realm = instance()

        val topicResult = realm.where(RealmTopicDTO::class.java).equalTo("id", withTopicDTO.id).findFirst() ?: return
        val bookmarkResult = realm.where(RealmImageBookmarkDTO::class.java).equalTo("topic.id", topicId).findAll() ?: return

        realm.beginTransaction()

        bookmarkResult.forEach {
            it.topic = topicResult
        }

        realm.commitTransaction()
    }
}