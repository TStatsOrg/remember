package com.app.dependencies.data.dao

import com.app.dependencies.data.dto.RealmTopicDTO
import com.app.shared.data.dao.TopicDAO
import com.app.shared.data.dto.TopicDTO

class RealmTopicDAO(override val instance: RealmProvider) : RealmDAO, TopicDAO {

    override fun insert(dto: TopicDTO) {
        val realm = instance()
        val roomDTO = RealmTopicDTO(id = dto.id, name = dto.name)
        realm.beginTransaction()
        realm.insertOrUpdate(roomDTO)
        realm.commitTransaction()
    }

    override fun getAll(): List<TopicDTO> {
        val realm = instance()
        val result = realm.where(RealmTopicDTO::class.java).findAll()
        return realm.copyFromRealm(result).toList()
    }

    override fun delete(topicId: Int) {
        val realm = instance()
        val result = realm.where(RealmTopicDTO::class.java).equalTo("id", topicId).findAll()
        realm.beginTransaction()
        result.deleteAllFromRealm()
        realm.commitTransaction()
    }
}