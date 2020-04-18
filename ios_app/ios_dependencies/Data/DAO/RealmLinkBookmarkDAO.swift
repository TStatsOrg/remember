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

public class RealmLinkBookmarkDAO: LinkBookmarkDAO {
    
    private let realm: Realm?
    
    public init(realm: Realm?){
        self.realm = realm
    }
    
    public func insert(dto_ dto: BookmarkDTOLinkBookmarkDTO) {
        let model = RealmLinkBookmarkDTO(id: dto.id, date: dto.date, url: dto.url, title: dto.title, icon: dto.icon, caption: dto.caption)
        try? realm?.write {
            realm?.add(model)
        }
    }
    
    public func getAll() -> [BookmarkDTOLinkBookmarkDTO] {
        let result = realm?.objects(RealmLinkBookmarkDTO.self)
        return result?.map { $0 } ?? []
    }
}
