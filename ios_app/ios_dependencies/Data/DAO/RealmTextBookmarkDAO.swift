//
//  RealmTextBookmarkDAO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 14/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import Realm
import RealmSwift

class RealmTextBookmarkDAO: RealmDAO, TextBookmarkDAO {
    
    let realm: Realm?
    
    public init(realm: Realm?){
        self.realm = realm
    }
    
    func insert(dto__ dto: BookmarkDTOTextBookmarkDTO) {
        try? realm?.write {
            realm?.add(dto.toObject(), update: Realm.UpdatePolicy.all)
        }
    }
    
    func getAll() -> [BookmarkDTOTextBookmarkDTO] {
        let result = realm?.objects(RealmTextBookmarkObject.self)
        return result?.map { $0 }.map { $0.toDTO() } ?? []
    }
}
