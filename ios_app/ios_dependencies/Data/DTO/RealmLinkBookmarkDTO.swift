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

class RealmLinkBookmarkDTO: Object, BookmarkDTOLinkBookmarkDTO {
    @objc dynamic var url: String = ""
    @objc dynamic var title: String? = nil
    @objc dynamic var icon: String? = nil
    @objc dynamic var id: Int32 = 0
    @objc dynamic var date: Int64 = 0
    @objc dynamic var caption: String? = nil 
    
    init(id: Int32, date: Int64, url: String, title: String?, icon: String?, caption: String?) {
        self.id = id
        self.date = date
        self.url = url
        self.title = title
        self.caption = caption
        self.icon = icon
    }

    required init() {}
}
