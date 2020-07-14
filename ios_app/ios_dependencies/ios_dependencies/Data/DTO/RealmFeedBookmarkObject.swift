//
//  RealmFeedBookmarkObject.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 06/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import RealmSwift

class RealmFeedBookmarkObject: Object {
    @objc dynamic var id: Int32 = 0
    @objc dynamic var url: String = ""
    @objc dynamic var title: String? = nil
    @objc dynamic var icon: String? = nil
    @objc dynamic var date: Int64 = 0
    @objc dynamic var latestUpdate: Int64 = 0
    @objc dynamic var caption: String? = nil
    @objc dynamic var isFavourite: Bool = false
    @objc dynamic var topic: RealmTopicObject? = nil
    
    init(id: Int32,
         date: Int64,
         latestUpdate: Int64,
         url: String,
         title: String?,
         icon: String?,
         caption: String?,
         isFavourite: Bool,
         topic: RealmTopicObject?) {
        self.id = id
        self.date = date
        self.latestUpdate = latestUpdate
        self.url = url
        self.title = title
        self.caption = caption
        self.icon = icon
        self.isFavourite = isFavourite
        self.topic = topic
    }

    required init() {}
    
    override class func primaryKey() -> String? {
        return "id"
    }
    
    internal class DTO: BookmarkDTOFeedBookmarkDTO {
        let caption: String?
        let icon: String?
        let title: String?
        let url: String
        let date: Int64
        let latestUpdate: Int64
        let id: Int32
        let isFavourite: Bool
        let topic: TopicDTO?
        
        init(id: Int32,
             url: String,
             title: String?,
             caption: String?,
             icon: String?,
             date: Int64,
             latestUpdate: Int64,
             isFavourite: Bool,
             topic: TopicDTO?) {
            self.id = id
            self.url = url
            self.title = title
            self.caption = caption
            self.icon = icon
            self.isFavourite = isFavourite
            self.date = date
            self.latestUpdate = latestUpdate
            self.topic = topic
        }
    }
}

extension RealmFeedBookmarkObject {
    func toDTO() -> BookmarkDTOFeedBookmarkDTO {
        return RealmFeedBookmarkObject.DTO(
            id: id,
            url: url,
            title: title,
            caption: caption,
            icon: icon,
            date: date,
            latestUpdate: latestUpdate,
            isFavourite: isFavourite,
            topic: topic?.toDTO()
        )
    }
}

extension BookmarkDTOFeedBookmarkDTO {
    func toObject() -> RealmFeedBookmarkObject {
        return RealmFeedBookmarkObject(
            id: id,
            date: date,
            latestUpdate: latestUpdate,
            url: url,
            title: title,
            icon: icon,
            caption: caption,
            isFavourite: isFavourite,
            topic: topic?.toObject()
        )
    }
}
