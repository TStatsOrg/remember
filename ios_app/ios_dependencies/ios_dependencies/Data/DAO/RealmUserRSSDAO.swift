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
        return []
    }
}
