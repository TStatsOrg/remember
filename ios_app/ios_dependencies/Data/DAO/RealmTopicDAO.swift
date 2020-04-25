//
//  RealmTopicDAO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 23/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import RealmSwift

class RealmTopicDAO: RealmDAO, TopicDAO {
    
    let realm: Realm?
    
    init(realm: Realm?) {
        self.realm = realm
    }
    
    func insert(dto___ dto: TopicDTO) {
        let model = RealmTopicDTO(id: dto.id, name: dto.name)
        try? realm?.write {
            realm?.add(model)
        }
    }
    
    func getAll() -> [TopicDTO] {
        let result = realm?.objects(RealmTopicDTO.self)
        return result?.map { $0 } ?? []
    }
}
