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

class RealmTextBookmarkDAO: TextBookmarkDAO {
    
    func insert(dto__ dto: BookmarkDTOTextBookmarkDTO) {
        let realm = try? Realm()
        let model = RealmTextBookmarkDTO(id: dto.id, date: dto.date, text: dto.text)
        try? realm?.write {
            realm?.add(model)
        }
    }
    
    func getAll() -> [BookmarkDTOTextBookmarkDTO] {
        let realm = try? Realm()
        let result = realm?.objects(RealmTextBookmarkDTO.self).filter("")
        return result?.map { $0 } ?? []
    }
}
