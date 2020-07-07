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
        // the store
        register { ReduxKt.store }
        // the navigations
        // register { AppNavigation() as Navigation }
        
        // the database
        register { RealmDatabase() as Database }
        
        // different utils
        register { iOSDeviceUtils() as DeviceUtils }
        register { SimpleUrlDownloader() as UrlDownloader }
        register { SystemCalendarUtils() as CalendarUtils }
        register { ExtensionContextDataCapture() as RawDataCapture }
        register { SwiftSoupHTMLDataProcess() as HTMLDataProcess }
        register { WebViewProvider() as WebViewProvider }
        register { WebViewContentResolver(provider: self.resolve()) as ContentResolver }
        register { iOSDataProcess(downloader: self.resolve(),
                                  resolver: self.resolve(),
                                  dataProcess: self.resolve()) as RawDataProcess }
        
        // data sources
        register {
            FeedKitRSSItemDataSource() as RSSItemDataSource
        }
        
        // repos
        register {
            SharedBookmarkRepository(imageBookmarkDAO: (self.resolve() as Database).getImageBookmarkDAO(),
                                     textBookmarkDAO: (self.resolve() as Database).getTextBookmarkDAO(),
                                     linkBookmarkDAO: (self.resolve() as Database).getLinkBookmarkDAO(),
                                     rssFeedBookmarkDAO: (self.resolve() as Database).getRSSFeedBookmarkDAO()) as BookmarkRepository
        }
        
        register {
            SharedTopicsRepository(topicDAO: (self.resolve() as Database).getTopicDAO()) as TopicsRepository
        }
        
        register {
            SharedRSSRepository(defaultRSSDAO: DefaultRSSDAO(),
                                userRSSDAO: (self.resolve() as Database).getUserRSSDAO(),
                                rssItemDataSource: self.resolve()) as RSSRepository
        }
        
        register {
            SharedRSSFeedBookmarkRepository(userBookmarkedRSSFeedDAO: (self.resolve() as Database).getRSSFeedBookmarkDAO(),
                                            allBookmarkRSSFeedDAO: DefaultRSSFeedBookmarkDAO()) as RSSFeedBookmarkRepository
        }
        
        // view models
        register {
            SharedPreviewViewModel(store: self.resolve(),
                                   bookmarkRepository: self.resolve(),
                                   calendar: self.resolve(),
                                   capture: self.resolve(),
                                   process: self.resolve()) as PreviewViewModel
        }
        
        register {
            SharedBookmarksViewModel(store: self.resolve(),
                                     calendar: self.resolve(),
                                     bookmarkRepository: self.resolve()) as BookmarksViewModel
        }
        register {
            SharedTopicsViewModel(store: self.resolve(),
                                  calendar: self.resolve(),
                                  topicsRepository: self.resolve(),
                                  bookmarkRepository: self.resolve()) as TopicsViewModel
        }
        
        register {
            SharedAddTopicViewModel(store: self.resolve(),
                                    topicsRepository: self.resolve()) as AddTopicViewModel
        }
        
        register {
            SharedEditBookmarkViewModel(store: self.resolve(),
                                        calendar: self.resolve(),
                                        bookmarkRepository: self.resolve(),
                                        topicsRepository: self.resolve()) as EditBookmarkViewModel
        }
        
        register {
            SharedEditTopicViewModel(store: self.resolve(),
                                     topicsRepository: self.resolve()) as EditTopicViewModel
        }
        
        register {
            SharedRSSViewModel(store: self.resolve(),
                               repository: self.resolve(),
                               calendarUtils: self.resolve()) as RSSViewModel
        }
        
        register {
            SharedRSSDetailViewModel(store: self.resolve(),
                                     feedBookmarkRepository: self.resolve(),
                                     bookmarkRepository: self.resolve(),
                                     repository: self.resolve()) as RSSDetailViewModel
        }
        
        register {
            SharedDisplayViewModel(store: self.resolve(),
                                   bookmarkRepository: self.resolve(),
                                   calendarUtils: self.resolve(),
                                   htmlDataProcess: self.resolve()) as DisplayViewModel
        }
        
        register {
            ShardHubViewModel(store: self.resolve(),
                              calendar: self.resolve(),
                              bookmarkRepository: self.resolve()) as HubViewModel
        }
        
        register {
            SharedFeedViewModel(store: self.resolve(),
                                bookmarkRepository: self.resolve(),
                                calendar: self.resolve()) as FeedViewModel
        }
    }
}
