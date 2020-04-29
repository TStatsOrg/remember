//
//  AppDependencyProvider.swift
//  ios_app
//
//  Created by Gabriel Coman on 29/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import ios_dependencies
import RememberShared

extension DependencyProvider {
    
    func provideServices() {
        // the store
        register { ReduxKt.store }
        // the navigations
        register { AppNavigation() as Navigation }
        
        // the database
        register { RealmDatabase() as Database }
        
        // different utils
        register { SystemCalendarUtils() as CalendarUtils }
        register { ExtensionContextDataCapture() as RawDataCapture }
        register { iOSDataProcess() as RawDataProcess }
        
        // repos
        register {
            SharedBookmarkRepository(imageBookmarkDAO: (self.resolve() as Database).getImageBookmarkDAO(),
                                     textBookmarkDAO: (self.resolve() as Database).getTextBookmarkDAO(),
                                     linkBookmarkDAO: (self.resolve() as Database).getLinkBookmarkDAO()) as BookmarkRepository
        }
        
        register {
            SharedTopicsRepository(topicDAO: (self.resolve() as Database).getTopicDAO() ) as TopicsRepository
        }
        
        // view models
        register {
            SharedPreviewViewModel(store: self.resolve(),
                                   bookmarkRepository: self.resolve(),
                                   calendar: self.resolve(),
                                   processor: self.resolve()) as PreviewViewModel
        }
        
        register {
            SharedMainHubViewModel(store: self.resolve(),
                                   calendar: self.resolve(),
                                   bookmarkRepository: self.resolve()) as MainHubViewModel
        }
        register {
            SharedTopicsViewModel(store: self.resolve(),
                                  calendar: self.resolve(),
                                  topicsRepository: self.resolve()) as TopicsViewModel
        }
    }
}
