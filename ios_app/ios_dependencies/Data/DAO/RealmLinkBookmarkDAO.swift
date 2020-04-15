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

class RealmLinkBookmarkDAO: LinkBookmarkDAO {
    
    func insert(dto_ dto: BookmarkDTOLinkBookmarkDTO) {
        let realm = try? Realm()
        let model = RealmLinkBookmarkDTO(id: dto.id, date: dto.date, url: dto.url, title: dto.title, icon: dto.icon, caption: dto.caption)
        try? realm?.write {
            realm?.add(model)
        }
    }
    
    func getAll() -> [BookmarkDTOLinkBookmarkDTO] {
        let realm = try? Realm()
        let result = realm?.objects(RealmLinkBookmarkDTO.self).filter("")
        return result?.map { $0 } ?? []
    }
}
