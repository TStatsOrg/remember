//
//  RealmLinkBookmarkDAO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 14/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import Realm
import RealmSwift

public class RealmLinkBookmarkDAO: RealmDAO, LinkBookmarkDAO {
    
    let realm: Realm?
    
    public init(realm: Realm?){
        self.realm = realm
    }
    
    public func insert(dto__ dto: BookmarkDTOLinkBookmarkDTO) {
        try? realm?.write {
            realm?.add(dto.toObject(), update: Realm.UpdatePolicy.all)
        }
    }
    
    public func getAll() -> [BookmarkDTOLinkBookmarkDTO] {
        let result = realm?.objects(RealmLinkBookmarkObject.self)
        return result?.map { $0 }.map { $0.toDTO() } ?? []
    }
    
    public func delete(bookmarkId: Int32) {
        let result = realm?.objects(RealmLinkBookmarkObject.self).filter { $0.id == bookmarkId }
        
        guard let dto = result?.first else {
            return
        }
        
        try? realm?.write {
            realm?.delete(dto)
        }
    }
    
    public func replaceTopic(topicId: Int32, withTopicDTO: TopicDTO) {
        let topicResult = realm?.objects(RealmTopicObject.self).filter { $0.id == withTopicDTO.id }
        let bookmarkResult = realm?.objects(RealmLinkBookmarkObject.self).filter { $0.topic?.id == topicId }
        
        guard let defaultTopic = topicResult?.first, let bookmarks = bookmarkResult else {
            return
        }
        
        try? realm?.write {
            bookmarks.forEach {
                $0.topic = defaultTopic
            }
        }
    }
}
