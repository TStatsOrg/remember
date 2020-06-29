//
//  RealmRSSObject.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 29/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import RealmSwift

class RealmRSSObject: Object {
    @objc dynamic var id: Int32 = 0
    @objc dynamic var caption: String? = nil
    @objc dynamic var icon: String? = nil
    @objc dynamic var isSubscribed: Bool = false
    @objc dynamic var link: String = ""
    @objc dynamic var title: String = ""
    
    init(id: Int32, link: String, title: String, isSubscribed: Bool, caption: String? = nil, icon: String? = nil) {
        self.id = id
        self.link = link
        self.title = title
        self.isSubscribed = isSubscribed
        self.caption = caption
        self.icon = icon
    }
    
    required init() {}
    
    override class func primaryKey() -> String? {
        return "id"
    }
    
    internal class DTO: RSSDTO {
        var caption: String?
        var icon: String?
        var id: Int32
        var isSubscribed: Bool
        var link: String
        var title: String
        
        init(id: Int32, link: String, title: String, isSubscribed: Bool, caption: String? = nil, icon: String? = nil) {
            self.id = id
            self.link = link
            self.title = title
            self.isSubscribed = isSubscribed
            self.caption = caption
            self.icon = icon
        }
    }
}

extension RealmRSSObject {
    
    func toDTO() -> RSSDTO {
        return RealmRSSObject.DTO(id: id, link: link, title: title, isSubscribed: isSubscribed, caption: caption, icon: icon)
    }
}

extension RSSDTO {
    
    func toObject() -> RealmRSSObject {
        return RealmRSSObject(id: id, link: link, title: title, isSubscribed: true, caption: caption, icon: icon)
    }
}
