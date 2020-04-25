//
//  RealmTopicDTO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 23/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import RealmSwift

class RealmTopicDTO: Object, TopicDTO {
    @objc dynamic var id: Int32 = 0
    @objc dynamic var name: String = ""
    
    init(id: Int32, name: String) {
        self.id = id
        self.name = name
    }
    
    required init() {}
}
