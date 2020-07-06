//
//  RealmRSSFeedBookmarkObject.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 06/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import RealmSwift

class RealmRSSFeedBookmarkObject: Object {
    @objc dynamic var id: Int32 = 0
    @objc dynamic var url: String = ""
    @objc dynamic var isSubscribed: Bool = false
    @objc dynamic var title: String? = nil
    @objc dynamic var icon: String? = nil
    @objc dynamic var date: Int64 = 0
    @objc dynamic var caption: String? = nil
    @objc dynamic var topic: RealmTopicObject? = nil
    
    init(id: Int32, date: Int64, url: String, title: String?, icon: String?, caption: String?, isSubscribed: Bool, topic: RealmTopicObject?) {
        self.id = id
        self.date = date
        self.url = url
        self.title = title
        self.caption = caption
        self.icon = icon
        self.isSubscribed = isSubscribed
        self.topic = topic
    }

    required init() {}
    
    override class func primaryKey() -> String? {
        return "id"
    }
    
    internal class DTO: BookmarkDTORSSFeedBookmarkDTO {
        var caption: String?
        var icon: String?
        var isSubscribed: Bool
        var title: String?
        var url: String
        var date: Int64
        var id: Int32
        var topic: TopicDTO?
        
        init(id: Int32,
             url: String,
             title: String?,
             caption: String?,
             icon: String?,
             isSubscribed: Bool,
             date: Int64,
             topic: TopicDTO?) {
            self.id = id
            self.url = url
            self.title = title
            self.caption = caption
            self.icon = icon
            self.isSubscribed = isSubscribed
            self.date = date
            self.topic = topic
        }
    }
}

extension RealmRSSFeedBookmarkObject {
    func toDTO() -> BookmarkDTORSSFeedBookmarkDTO {
        return RealmRSSFeedBookmarkObject.DTO(id: id, url: url, title: title, caption: caption, icon: icon, isSubscribed: isSubscribed, date: date, topic: topic?.toDTO())
    }
}

extension BookmarkDTORSSFeedBookmarkDTO {
    func toObject() -> RealmRSSFeedBookmarkObject {
        return RealmRSSFeedBookmarkObject(id: id, date: date, url: url, title: title, icon: icon, caption: caption, isSubscribed: isSubscribed, topic: topic?.toObject())
    }
}
