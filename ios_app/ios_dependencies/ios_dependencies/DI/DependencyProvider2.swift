//
//  DependencyProvider2.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 09/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import Social
import RealmSwift

public typealias DependencyBuilder<T> = () -> T 

public class DependencyModule {
 
    private var builders: [ObjectIdentifier: DependencyBuilder<Any>] = [:]
    
    public init(registerBlock block: @escaping (DependencyModule) -> Void) {
        block(self)
    }
    
    public func register<T>(builder: @escaping DependencyBuilder<T>) {
        let key = ObjectIdentifier(T.self)
        builders[key] = builder
    }
    
    public func resolve<T>() -> T? {
        let key = ObjectIdentifier(T.self)
        
        guard let component = builders[key]?() as? T else {
            return nil
        }
        
        return component
    }
}

public class DependencyLocator {
    
    private var modules: [DependencyModule] = []
    
    public static let shared = DependencyLocator()
    
    public func register(module: () -> DependencyModule) {
        modules.append(module())
    }
    
    public func resolve<T>() -> T {
        
        let result = modules.compactMap { (module) -> T? in
            return module.resolve()
        }.first
        
        guard let component = result else {
            fatalError("Dependency '\(T.self)' could not be resolved!")
        }
        
        return component
    }
}
