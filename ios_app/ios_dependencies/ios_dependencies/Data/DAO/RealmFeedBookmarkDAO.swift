//
//  RealmFeedBookmarkDAO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 06/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import Realm
import RealmSwift

public class RealmFeedBookmarkDAO: RealmDAO, FeedBookmarkDAO {
    
    var realm: Realm?
    
    public init(realm: Realm?) {
        self.realm = realm
    }
    
    public func delete(bookmarkId: Int32) {
        let result = realm?.objects(RealmFeedBookmarkObject.self).filter { $0.id == bookmarkId }
        
        guard let dto = result?.first else {
            return
        }
        
        try? realm?.write {
            realm?.delete(dto)
        }
    }
    
    public func getAll() -> [BookmarkDTOFeedBookmarkDTO] {
        let result = realm?.objects(RealmFeedBookmarkObject.self)
        return result?.map { $0 }.map { $0.toDTO() } ?? []
    }
    
    public func insert(dto: BookmarkDTOFeedBookmarkDTO) {
        try? realm?.write {
            realm?.add(dto.toObject(), update: Realm.UpdatePolicy.all)
        }
    }
    
    public func replaceTopic(topicId: Int32, withTopicDTO: TopicDTO) {
        let topicResult = realm?.objects(RealmTopicObject.self).filter { $0.id == withTopicDTO.id }
        let bookmarkResult = realm?.objects(RealmFeedBookmarkObject.self).filter { $0.topic?.id == topicId }
        
        guard let defaultTopic = topicResult?.first, let bookmarks = bookmarkResult else {
            return
        }
        
        try? realm?.write {
            bookmarks.forEach {
                $0.topic = defaultTopic
            }
        }
    }
    
    public func update(withNewDates: [FeedUpdateDTO]) {
        let bookmarkIds = withNewDates.map { $0.id }
        let bookmarkResult = realm?.objects(RealmFeedBookmarkObject.self).filter { bookmarkIds.contains($0.id) }
        
        guard let bookmarks = bookmarkResult else {
            return
        }
        
        var valueMap: [Int32: Int64] = [:]
        withNewDates.forEach {
            valueMap[$0.id] = $0.lastUpdate
        }
        
        try? realm?.write {
            bookmarks.forEach {
                if let latestUpdate = valueMap[$0.id] {
                    $0.latestUpdate = latestUpdate
                }
            }
        }
    }
}
