package com.app.dependencies.data.dao

import android.content.Context
import com.app.shared.data.dao.Database
import com.app.shared.data.dao.ImageBookmarkDAO
import com.app.shared.data.dao.LinkBookmarkDAO
import com.app.shared.data.dao.TextBookmarkDAO
import io.realm.Realm

class RealmDatabase(context: Context): Database {

    init {
        Realm.init(context)
    }

    private fun getInstance(): Realm = Realm.getDefaultInstance()

    override fun getImageBookmarkDAO(): ImageBookmarkDAO = RealmImageBookmarkDAO(instance = ::getInstance)
    override fun getLinkBookmarkDAO(): LinkBookmarkDAO = RealmLinkBookmarkDAO(instance = ::getInstance)
    override fun getTextBookmarkDAO(): TextBookmarkDAO = RealmTextBookmarkDAO(instance = ::getInstance)
}