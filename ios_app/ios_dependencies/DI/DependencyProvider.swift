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

public class DependencyProvider {
    
    public static let shared = DependencyProvider()
    
    private lazy var config: Realm.Configuration = {
            
        if var directory: URL = FileManager.default.containerURL(forSecurityApplicationGroupIdentifier:"group.app.remember") {
            directory.appendPathComponent("db.realm", isDirectory: true)
            return Realm.Configuration(fileURL: directory, schemaVersion: 1)
        } else {
            return Realm.Configuration.defaultConfiguration
        }
    }()
    
    public lazy var database: Realm? = {
        return try? Realm(configuration: config)
    }()
    
    func getCalendarUtils() -> CalendarUtils {
        return SystemCalendarUtils()
    }
    
    public func getProcessor() -> RawDataProcess {
        return iOSDataProcess()
    }
    
    public func getBookmarkRepository() -> BookmarkRepository {
        return SharedBookmarkRepository(imageBookmarkDAO: RealmImageBookmarkDAO(realm: database),
                                        textBookmarkDAO: RealmTextBookmarkDAO(realm: database),
                                        linkBookmarkDAO: RealmLinkBookmarkDAO(realm: database))
    }
    
    public func getDataCapture(context: NSExtensionContext?) -> RawDataCapture {
        return ExtensionContextDataCapture(withExtensionContext: context)
    }
    
    public func getPreviewViewModel() -> PreviewViewModel {
        return SharedPreviewViewModel(store: ReduxKt.store,
                                      bookmarkRepository: getBookmarkRepository(),
                                      calendar: getCalendarUtils(),
                                      processor: getProcessor())
    }
}
