package com.app.dependencies.data.dao

import android.content.Context
import com.app.shared.data.dao.ImageBookmarkDAO
import com.app.shared.data.dao.LinkBookmarkDAO
import com.app.shared.data.dao.TextBookmarkDAO
import io.realm.Realm

object Database {

    private val instance by lazy {
        Realm.getDefaultInstance()
    }

    fun init(context: Context) {
        Realm.init(context)
    }

    fun getImageDAO(): ImageBookmarkDAO = RealmImageBookmarkDAO(realm = instance)
    fun getLinkDAO(): LinkBookmarkDAO = RealmLinkBookmarkDAO(realm = instance)
    fun getTextDAO(): TextBookmarkDAO = RealmTextBookmarkDAO(realm = instance)
}