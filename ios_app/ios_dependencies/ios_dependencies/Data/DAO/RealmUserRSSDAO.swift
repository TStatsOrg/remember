//
//  RealmRSSDAO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 17/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import RealmSwift

class RealmUserRSSDAO: RealmDAO, RSSDAO {
    
    let realm: Realm?
    
    init(realm: Realm?) {
        self.realm = realm
    }
    
    func getAll() -> [RSSDTO] {
        let result = realm?.objects(RealmRSSObject.self)
        return result?.map { $0 }.map { $0.toDTO() } ?? []
    }
    
    func get(rssId: Int32) -> RSSDTO? {
        let result = realm?.objects(RealmRSSObject.self).map { $0 }.filter { $0.id == rssId }
        return result?.first?.toDTO()
    }
    
    func delete(rssId: Int32) {
        let result = realm?.objects(RealmRSSObject.self).filter { $0.id == rssId }
        try? realm?.write {
            if let dto = result {
                realm?.delete(dto)
            }
        }
    }
    
    func insert(dto: RSSDTO) {
        try? realm?.write {
            realm?.add(dto.toObject(), update: Realm.UpdatePolicy.all)
        }
    }
}
