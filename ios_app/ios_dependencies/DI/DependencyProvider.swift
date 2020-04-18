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
    
    private init() {
        register { SystemCalendarUtils() as CalendarUtils }
        register { ExtensionContextDataCapture() as RawDataCapture }
        register { iOSDataProcess() as RawDataProcess }
        register { ReduxKt.store }
        register { RealmDatabase() as Database }
        register { SharedBookmarkRepository(imageBookmarkDAO: (self.resolve() as Database).getImageBookmarkDAO(),
                                            textBookmarkDAO: (self.resolve() as Database).getTextBookmarkDAO(),
                                            linkBookmarkDAO: (self.resolve() as Database).getLinkBookmarkDAO()) as BookmarkRepository }
        register { SharedPreviewViewModel(store: self.resolve(),
                                          bookmarkRepository: self.resolve(),
                                          calendar: self.resolve(),
                                          processor: self.resolve()) as PreviewViewModel }
        register { SharedMainHubViewModel(store: self.resolve(),
                                          calendar: self.resolve(),
                                          bookmarkRepository: self.resolve()) as MainHubViewModel }
    }
}
