package com.app.dependencies.data.dao

import com.app.dependencies.data.dto.RealmLinkBookmarkDTO
import com.app.dependencies.data.dto.RealmTopicDTO
import com.app.shared.data.dao.LinkBookmarkDAO
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

class RealmLinkBookmarkDAO(override val instance: RealmProvider): RealmDAO, LinkBookmarkDAO {

    override fun getAll(): List<BookmarkDTO.LinkBookmarkDTO> {
        val realm = instance()
        val result = realm.where(RealmLinkBookmarkDTO::class.java).findAll()
        return realm.copyFromRealm(result).toList()
    }

    override fun insert(dto: BookmarkDTO.LinkBookmarkDTO) {
        val realm = instance()

        val topicDTO = dto.topic?.let {
            RealmTopicDTO(id = it.id, name = it.name)
        }

        val roomDTO = RealmLinkBookmarkDTO(
            id = dto.id,
            date = dto.date,
            title = dto.title,
            caption = dto.caption,
            icon = dto.icon,
            url = dto.url,
            topic = topicDTO
        )

        realm.beginTransaction()
        realm.insertOrUpdate(roomDTO)
        realm.commitTransaction()
    }

    override fun delete(bookmarkId: Int) {
        val realm = instance()
        val result = realm.where(RealmLinkBookmarkDTO::class.java).equalTo("id", bookmarkId).findAll()
        realm.beginTransaction()
        result.deleteAllFromRealm()
        realm.commitTransaction()
    }

    override fun replaceTopic(topicId: Int, withTopicDTO: TopicDTO) {
        val realm = instance()

        val topicResult = realm.where(RealmTopicDTO::class.java).equalTo("id", withTopicDTO.id).findFirst() ?: return
        val bookmarkResult = realm.where(RealmLinkBookmarkDTO::class.java).equalTo("topic.id", topicId).findAll() ?: return

        realm.beginTransaction()

        bookmarkResult.forEach {
            it.topic = topicResult
        }

        realm.commitTransaction()
    }
}