//
//  DependencyProvider.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 14/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import Social
import RealmSwift

@propertyWrapper
public struct Injected<Service> {
    
    public private(set) var wrappedValue: Service
    
    public init() {
        wrappedValue = DependencyProvider.shared.resolve()
    }
}

public typealias DependencyFactory<T> = () -> T

public class DependencyProvider {
    
    public static let shared = DependencyProvider()
    
    private var factories: [ObjectIdentifier: DependencyFactory<Any>] = [:]
    
    public func register<T>(factory: @escaping DependencyFactory<T>) {
        let key = ObjectIdentifier(T.self)
        factories[key] = factory
    }
    
    public func resolve<T>() -> T {
        let key = ObjectIdentifier(T.self)
        
        guard let component = factories[key]?() as? T else {
            fatalError("Dependency '\(T.self)' could not be resolved!")
        }
        
        return component
    }
}
