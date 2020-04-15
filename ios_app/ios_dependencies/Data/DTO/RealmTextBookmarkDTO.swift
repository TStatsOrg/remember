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

class RealmTextBookmarkDTO: Object, BookmarkDTOTextBookmarkDTO {
    @objc dynamic var text: String = ""
    @objc dynamic var id: Int32 = 0
    @objc dynamic var date: Int64 = 0
    
    init(id: Int32, date: Int64, text: String) {
        self.id = id
        self.date = date
        self.text = text
    }
    
    required init() {
        fatalError("init() has not been implemented")
    }
}
