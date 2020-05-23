package com.app.dependencies.data.dao

import com.app.dependencies.data.dto.RealmTextBookmarkDTO
import com.app.dependencies.data.dto.RealmTopicDTO
import com.app.shared.data.dao.TextBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

class RealmTextBookmarkDAO(override val instance: RealmProvider): RealmDAO, TextBookmarkDAO {

    override fun getAll(): List<BookmarkDTO.TextBookmarkDTO> {
        val realm = instance()
        val result = realm.where(RealmTextBookmarkDTO::class.java).findAll()
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

    override fun delete(bookmarkId: Int) {
        val realm = instance()
        val result = realm.where(RealmTextBookmarkDTO::class.java).equalTo("id", bookmarkId).findAll()
        realm.beginTransaction()
        result.deleteAllFromRealm()
        realm.commitTransaction()
    }

    override fun replaceTopic(topicId: Int, withTopicDTO: TopicDTO) {
        val realm = instance()

        val topicResult = realm.where(RealmTopicDTO::class.java).equalTo("id", withTopicDTO.id).findFirst() ?: return
        val bookmarkResult = realm.where(RealmTextBookmarkDTO::class.java).equalTo("topic.id", topicId).findAll() ?: return

        realm.beginTransaction()

        bookmarkResult.forEach {
            it.topic = topicResult
        }

        realm.commitTransaction()
    }
}