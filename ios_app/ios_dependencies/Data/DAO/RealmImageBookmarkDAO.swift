//
//  RealmImageBookmarkDAO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 14/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import Realm
import RealmSwift

class RealmImageBookmarkDAO: RealmDAO, ImageBookmarkDAO {
    
    let realm: Realm?
    
    public init(realm: Realm?){
        self.realm = realm
    }
    
    func insert(dto: BookmarkDTOImageBookmarkDTO) {
        try? realm?.write {
            realm?.add(dto.toObject())
        }
    }
    
    func getAll() -> [BookmarkDTOImageBookmarkDTO] {
        let result = realm?.objects(RealmImageBookmarkObject.self)
        return result?.map { $0 }.map { $0.toDTO() } ?? []
    }
}
