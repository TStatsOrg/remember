package com.app.dependencies.data.dto

import com.app.shared.data.dto.TopicDTO
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmTopicDTO(
    @PrimaryKey
    override var id: Int = 0,
    override var name: String = ""
): RealmObject(), TopicDTO