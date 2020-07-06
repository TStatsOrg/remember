//
//  RealmTextBookmarkDAO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 14/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import Realm
import RealmSwift

class RealmTextBookmarkDAO: RealmDAO, TextBookmarkDAO {
    
    let realm: Realm?
    
    public init(realm: Realm?){
        self.realm = realm
    }
    
    func insert(dto____ dto: BookmarkDTOTextBookmarkDTO) {
        try? realm?.write {
            realm?.add(dto.toObject(), update: Realm.UpdatePolicy.all)
        }
    }
    
    func getAll() -> [BookmarkDTOTextBookmarkDTO] {
        let result = realm?.objects(RealmTextBookmarkObject.self)
        return result?.map { $0 }.map { $0.toDTO() } ?? []
    }
    
    func delete(bookmarkId: Int32) {
        let result = realm?.objects(RealmTextBookmarkObject.self).filter { $0.id == bookmarkId }
        
        guard let dto = result?.first else {
            return
        }
        
        try? realm?.write {
            realm?.delete(dto)
        }
    }
    
    func replaceTopic(topicId: Int32, withTopicDTO: TopicDTO) {
        let topicResult = realm?.objects(RealmTopicObject.self).filter { $0.id == withTopicDTO.id }
        let bookmarkResult = realm?.objects(RealmTextBookmarkObject.self).filter { $0.topic?.id == topicId }
        
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
