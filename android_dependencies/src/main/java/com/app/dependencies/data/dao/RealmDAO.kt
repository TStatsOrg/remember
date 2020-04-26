package com.app.dependencies.data.dao

import io.realm.Realm

typealias RealmProvider = () -> Realm

interface RealmDAO {
    val instance: RealmProvider
}