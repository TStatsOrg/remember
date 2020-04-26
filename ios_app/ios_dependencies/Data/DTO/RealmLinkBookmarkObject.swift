//
//  RealmLinkBookmarkDTO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 14/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import RealmSwift

class RealmLinkBookmarkObject: Object {
    @objc dynamic var url: String = ""
    @objc dynamic var title: String? = nil
    @objc dynamic var icon: String? = nil
    @objc dynamic var id: Int32 = 0
    @objc dynamic var date: Int64 = 0
    @objc dynamic var caption: String? = nil
    @objc dynamic var topic: RealmTopicObject? = nil
    
    init(id: Int32, date: Int64, url: String, title: String?, icon: String?, caption: String?, topic: RealmTopicObject?) {
        self.id = id
        self.date = date
        self.url = url
        self.title = title
        self.caption = caption
        self.icon = icon
        self.topic = topic
    }

    required init() {}
    
    internal class DTO: BookmarkDTOLinkBookmarkDTO {
        let url: String
        let title: String?
        let caption: String?
        let icon: String?
        let id: Int32
        let date: Int64
        let topic: TopicDTO?
        
        init(id: Int32, date: Int64, url: String, title: String?, icon: String?, caption: String?, topic: TopicDTO?) {
            self.id = id
            self.date = date
            self.url = url
            self.title = title
            self.caption = caption
            self.icon = icon
            self.topic = topic
        }
    }
}

extension RealmLinkBookmarkObject {
    func toDTO() -> BookmarkDTOLinkBookmarkDTO {
        return RealmLinkBookmarkObject.DTO(id: id, date: date, url: url, title: title, icon: icon, caption: caption, topic: topic?.toDTO())
    }
}

extension BookmarkDTOLinkBookmarkDTO {
    func toObject() -> RealmLinkBookmarkObject {
        return RealmLinkBookmarkObject(id: id, date: date, url: url, title: title, icon: icon, caption: caption, topic: topic?.toObject())
    }
}
