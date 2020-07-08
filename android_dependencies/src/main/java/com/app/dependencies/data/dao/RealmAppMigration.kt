package com.app.dependencies.data.dao

import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration

class RealmAppMigration: RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {

        // DynamicRealm exposes an editable schema
        val schema = realm.schema

        /**
         * In version 2L we added a new field: "isFavourite", to all the BookmarkDTOs
         */
        if (oldVersion < 2L) {
            schema.create("RealmFeedBookmarkDTO")
                ?.addField("id", Int::class.java, FieldAttribute.PRIMARY_KEY)
                ?.addField("date", Long::class.java)
                ?.addField("isFavourite", Boolean::class.java)
                ?.addField("url", String::class.java, FieldAttribute.REQUIRED)
                ?.addField("title", String::class.java)
                ?.addField("caption", String::class.java)
                ?.addField("icon", String::class.java)
                ?.addRealmObjectField("topic", schema.get("RealmTopicDTO")!!)

            schema.get("RealmImageBookmarkDTO")
                ?.addField("isFavourite", Boolean::class.java)

            schema.get("RealmLinkBookmarkDTO")
                ?.addField("isFavourite", Boolean::class.java)

            schema.get("RealmTextBookmarkDTO")
                ?.addField("isFavourite", Boolean::class.java)
        }
    }
}