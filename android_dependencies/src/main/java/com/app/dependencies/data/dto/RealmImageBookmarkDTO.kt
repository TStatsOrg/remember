package com.app.dependencies.data.dto

import com.app.shared.data.dto.BookmarkDTO
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmImageBookmarkDTO(
    @PrimaryKey
    override var id: Int = 0,
    override var date: Long = 0L,
    override var url: String = ""
) : RealmObject(), BookmarkDTO.ImageBookmarkDTO