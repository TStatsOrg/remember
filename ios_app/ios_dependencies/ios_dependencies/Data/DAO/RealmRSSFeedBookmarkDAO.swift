//
//  RealmRSSFeedBookmarkDAO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 06/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import Realm
import RealmSwift

public class RealmRSSFeedBookmarkDAO: RealmDAO, RSSFeedBookmarkDAO {
    
    var realm: Realm?
    
    public init(realm: Realm?){
        self.realm = realm
    }
    
    public func delete(bookmarkId: Int32) {
        let result = realm?.objects(RealmRSSFeedBookmarkObject.self).filter { $0.id == bookmarkId }
        
        guard let dto = result?.first else {
            return
        }
        
        try? realm?.write {
            realm?.delete(dto)
        }
    }
    
    public func getAll() -> [BookmarkDTORSSFeedBookmarkDTO] {
        let result = realm?.objects(RealmRSSFeedBookmarkObject.self)
        return result?.map { $0 }.map { $0.toDTO() } ?? []
    }
    
    public func insert(dto___ dto: BookmarkDTORSSFeedBookmarkDTO) {
        try? realm?.write {
            realm?.add(dto.toObject(), update: Realm.UpdatePolicy.all)
        }
    }
    
    public func replaceTopic(topicId: Int32, withTopicDTO: TopicDTO) {
        let topicResult = realm?.objects(RealmTopicObject.self).filter { $0.id == withTopicDTO.id }
        let bookmarkResult = realm?.objects(RealmRSSFeedBookmarkObject.self).filter { $0.topic?.id == topicId }
        
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
