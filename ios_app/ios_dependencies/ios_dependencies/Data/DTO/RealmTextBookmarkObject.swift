//
//  RealmTextBookmarkDTO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 14/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import RealmSwift

class RealmTextBookmarkObject: Object {
    @objc dynamic var id: Int32 = 0
    @objc dynamic var text: String = ""
    @objc dynamic var date: Int64 = 0
    @objc dynamic var isFavourite: Bool = false
    @objc dynamic var topic: RealmTopicObject? = nil
    
    init(id: Int32, date: Int64, text: String, isFavourite: Bool, topic: RealmTopicObject?) {
        self.id = id
        self.date = date
        self.text = text
        self.isFavourite = isFavourite
        self.topic = topic
    }
    
    required init() {}
    
    override class func primaryKey() -> String? {
        return "id"
    }
    
    internal class DTO: BookmarkDTOTextBookmarkDTO {
        let text: String
        let id: Int32
        let date: Int64
        let isFavourite: Bool
        let topic: TopicDTO?
        
        init(id: Int32, date: Int64, text: String, isFavourite: Bool, topic: TopicDTO?) {
            self.id = id
            self.date = date
            self.text = text
            self.isFavourite = isFavourite
            self.topic = topic
        }
    }
}

extension RealmTextBookmarkObject {
    func toDTO() -> BookmarkDTOTextBookmarkDTO {
        return RealmTextBookmarkObject.DTO(id: id, date: date, text: text, isFavourite: isFavourite, topic: topic?.toDTO())
    }
}

extension BookmarkDTOTextBookmarkDTO {
    func toObject() -> RealmTextBookmarkObject {
        return RealmTextBookmarkObject(id: id, date: date, text: text, isFavourite: isFavourite, topic: topic?.toObject())
    }
}
