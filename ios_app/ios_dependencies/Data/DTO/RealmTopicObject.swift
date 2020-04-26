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

class RealmTopicObject: Object {
    @objc dynamic var id: Int32 = 0
    @objc dynamic var name: String = ""
    
    init(id: Int32, name: String) {
        self.id = id
        self.name = name
    }
    
    required init() {}
    
    internal class DTO: TopicDTO {
        let id: Int32
        let name: String
        
        init(id: Int32, name: String) {
            self.id = id
            self.name = name
        }
    }
}

extension RealmTopicObject {
    func toDTO() -> TopicDTO {
        return RealmTopicObject.DTO(id: id, name: name)
    }
}

extension TopicDTO {
    func toObject() -> RealmTopicObject {
        return RealmTopicObject(id: id, name: name)
    }
}
