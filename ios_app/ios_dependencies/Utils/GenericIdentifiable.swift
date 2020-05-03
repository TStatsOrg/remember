//
//  GenericIdentifiable.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 03/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit

/// A protocol that will add a static identifier to each implementor.
/// Useful for cells and the like
public protocol GenericIdentifiable {
    static var identifier: String { get }
}

extension GenericIdentifiable {
    
    public static var identifier: String {
        return String(describing: self)
    }
    
    public static func register(_ collectionView: UICollectionView) {
        guard let anyClass = self as? AnyClass else {
            return
        }
        
        collectionView.register(anyClass, forCellWithReuseIdentifier: self.identifier)
    }
    
    public static func register(_ tableView: UITableView) {
        guard let anyClass = self as? AnyClass else {
            return
        }
        
        tableView.register(anyClass, forCellReuseIdentifier: self.identifier)
    }
}
