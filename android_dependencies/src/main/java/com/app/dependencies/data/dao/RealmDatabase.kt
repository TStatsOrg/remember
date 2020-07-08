package com.app.dependencies.data.dao

import android.content.Context
import com.app.shared.data.dao.*
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmDatabase(context: Context): Database {

    private val config: RealmConfiguration by lazy {
        RealmConfiguration.Builder()
            .schemaVersion(2L)
            .migration(RealmAppMigration())
            .build()
    }

    init {
        Realm.init(context)
    }

    private fun getInstance(): Realm = Realm.getInstance(config)

    override fun getImageBookmarkDAO(): ImageBookmarkDAO = RealmImageBookmarkDAO(instance = ::getInstance)
    override fun getLinkBookmarkDAO(): LinkBookmarkDAO = RealmLinkBookmarkDAO(instance = ::getInstance)
    override fun getTextBookmarkDAO(): TextBookmarkDAO = RealmTextBookmarkDAO(instance = ::getInstance)
    override fun getTopicDAO(): TopicDAO = RealmTopicDAO(instance = ::getInstance)
    override fun getFeedBookmarkDAO(): FeedBookmarkDAO = RealmFeedBookmarkDAO(instance = ::getInstance)
}