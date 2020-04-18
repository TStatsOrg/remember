//
//  RealmDatabase.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 18/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import RealmSwift

class RealmDatabase: NSObject {
    
    private static let GROUP_ID = "group.app.remember"
    private static let PATH_COMPONENT = "db.realm"
    
    private lazy var config: Realm.Configuration = {
            
        if var directory: URL = FileManager.default.containerURL(forSecurityApplicationGroupIdentifier:RealmDatabase.GROUP_ID) {
            directory.appendPathComponent(RealmDatabase.PATH_COMPONENT, isDirectory: true)
            return Realm.Configuration(fileURL: directory, schemaVersion: 1)
        } else {
            return Realm.Configuration.defaultConfiguration
        }
    }()
    
    internal lazy var database: Realm? = {
        return try? Realm(configuration: config)
    }()
}

// MARK: Database

extension RealmDatabase: Database {
    
    func getImageBookmarkDAO() -> ImageBookmarkDAO {
        return RealmImageBookmarkDAO(realm: database)
    }
    
    func getLinkBookmarkDAO() -> LinkBookmarkDAO {
        return RealmLinkBookmarkDAO(realm: database)
    }
    
    func getTextBookmarkDAO() -> TextBookmarkDAO {
        return RealmTextBookmarkDAO(realm: database)
    }
}
