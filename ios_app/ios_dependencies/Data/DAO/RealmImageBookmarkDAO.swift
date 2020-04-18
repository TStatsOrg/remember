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

class RealmImageBookmarkDAO: ImageBookmarkDAO {
    
    private let realm: Realm?
    
    public init(realm: Realm?){
        self.realm = realm
    }
    
    func insert(dto: BookmarkDTOImageBookmarkDTO) {
        let model = RealmImageBookmarkDTO(id: dto.id, date: dto.date, url: dto.url)
        try? realm?.write {
            realm?.add(model)
        }
    }
    
    func getAll() -> [BookmarkDTOImageBookmarkDTO] {
        let result = realm?.objects(RealmImageBookmarkDTO.self)//.filter("")
        return result?.map { $0 } ?? []
    }
}
