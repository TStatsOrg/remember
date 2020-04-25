//
//  RealmLinkBookmarkDAO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 14/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import Realm
import RealmSwift

public class RealmLinkBookmarkDAO: RealmDAO, LinkBookmarkDAO {
    
    let realm: Realm?
    
    public init(realm: Realm?){
        self.realm = realm
    }
    
    public func insert(dto_ dto: BookmarkDTOLinkBookmarkDTO) {
        try? realm?.write {
            realm?.add(dto.toObject())
        }
    }
    
    public func getAll() -> [BookmarkDTOLinkBookmarkDTO] {
        let result = realm?.objects(RealmLinkBookmarkObject.self)
        return result?.map { $0 }.map { $0.toDTO() } ?? []
    }
}
