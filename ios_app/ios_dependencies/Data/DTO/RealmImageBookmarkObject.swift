//
//  RealmImageBookmarkDTO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 14/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import RealmSwift

class RealmImageBookmarkObject: Object {
    @objc dynamic var id: Int32 = 0
    @objc dynamic var url: String = ""
    @objc dynamic var date: Int64 = 0
    @objc dynamic var topic: RealmTopicObject? = nil
    
    init(id: Int32, date: Int64, url: String, topic: RealmTopicObject?) {
        self.id = id
        self.date = date
        self.url = url
        self.topic = topic
    }
    
    required init() {}
    
    override class func primaryKey() -> String? {
        return "id"
    }
    
    internal class DTO: BookmarkDTOImageBookmarkDTO {
        let url: String
        let id: Int32
        let date: Int64
        let topic: TopicDTO?
        
        init(id: Int32, date: Int64, url: String, topic: TopicDTO?) {
            self.id = id
            self.date = date
            self.url = url
            self.topic = topic
        }
    }
}

extension BookmarkDTOImageBookmarkDTO {
    func toObject() -> RealmImageBookmarkObject {
        return RealmImageBookmarkObject(id: id, date: date, url: url, topic: topic?.toObject())
    }
}

extension RealmImageBookmarkObject {
    func toDTO() -> BookmarkDTOImageBookmarkDTO {
        return RealmImageBookmarkObject.DTO(id: id, date: date, url: url, topic: topic?.toDTO())
    }
}
