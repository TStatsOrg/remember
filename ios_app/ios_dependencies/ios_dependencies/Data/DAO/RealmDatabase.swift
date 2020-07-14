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

public class RealmDatabase: NSObject {
    
    private static let GROUP = "group.app.keepjar"
    private static let PATH = "db.realm"
    
    private lazy var config: Realm.Configuration = {
            
        if var directory = FileManager.default.containerURL(forSecurityApplicationGroupIdentifier: RealmDatabase.GROUP) {
            directory.appendPathComponent(RealmDatabase.PATH, isDirectory: true)
            return Realm.Configuration(fileURL: directory,
                                       schemaVersion: 4,
                                       migrationBlock: { migration, oldSchemaVersion in
                
                // migrate and add the "latestUpdate" field
                if oldSchemaVersion < 4 {
                    migration.enumerateObjects(ofType: RealmFeedBookmarkObject.className()) { (oldObject, newObject) in
                        newObject?["latestUpdate"] = oldObject!["date"]
                    }
                }
            })
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
    
    public func getImageBookmarkDAO() -> ImageBookmarkDAO {
        return RealmImageBookmarkDAO(realm: database)
    }
    
    public func getLinkBookmarkDAO() -> LinkBookmarkDAO {
        return RealmLinkBookmarkDAO(realm: database)
    }
    
    public func getTextBookmarkDAO() -> TextBookmarkDAO {
        return RealmTextBookmarkDAO(realm: database)
    }
    
    public func getFeedBookmarkDAO() -> FeedBookmarkDAO {
        return RealmFeedBookmarkDAO(realm: database)
    }
    
    public func getTopicDAO() -> TopicDAO {
        return RealmTopicDAO(realm: database)
    }
}
