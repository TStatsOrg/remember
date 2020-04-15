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

public class DependencyProvider {
    
    static func getCalendarUtils() -> CalendarUtils {
        return SystemCalendarUtils()
    }
    
    static func getProcessor() -> RawDataProcess {
        return iOSDataProcess()
    }
    
    static func getBookmarkRepository() -> BookmarkRepository {
        return SharedBookmarkRepository(imageBookmarkDAO: RealmImageBookmarkDAO(),
                                        textBookmarkDAO: RealmTextBookmarkDAO(),
                                        linkBookmarkDAO: RealmLinkBookmarkDAO())
    }
    
    public static func getDataCapture(context: NSExtensionContext?) -> RawDataCapture {
        return ExtensionContextDataCapture(withExtensionContext: context)
    }
    
    public static func getPreviewViewModel() -> PreviewViewModel {
        return SharedPreviewViewModel(store: ReduxKt.store,
                                      bookmarkRepository: getBookmarkRepository(),
                                      calendar: getCalendarUtils(),
                                      processor: getProcessor())
    }
}
