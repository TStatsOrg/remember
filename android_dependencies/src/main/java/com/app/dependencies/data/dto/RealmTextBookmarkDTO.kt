package com.app.dependencies.data.dto

import com.app.shared.data.dto.BookmarkDTO
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmTextBookmarkDTO(
    @PrimaryKey
    override var id: Int = 0,
    override var date: Long = 0L,
    override var text: String = "",
    override var topic: RealmTopicDTO? = null,
    override var isFavourite: Boolean = false
) : RealmObject(), BookmarkDTO.TextBookmarkDTO