//
//  RealmDAO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 23/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RealmSwift

protocol RealmDAO {
    var realm: Realm? { get }
}
