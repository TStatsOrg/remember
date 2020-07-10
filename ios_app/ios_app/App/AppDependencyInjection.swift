//
//  AppDependencyInjection.swift
//  ios_app
//
//  Created by Gabriel Coman on 09/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import Social
import RealmSwift
import ios_dependencies
import ios_views

extension DependencyLocator {
    
    func setup() {
        registerUIModule()
        registerReduxModule()
        registerDatabaseModule()
        registerUtilsModule()
        registerDataSourceModule()
        registerReposModule()
        registerViewModelsModule()
    }
}

extension DependencyLocator {
    
    func registerUIModule() {
        register {
            DependencyModule {
                $0.register { AppScreenFactory() as ScreenFactory }
                $0.register { AppNavigation() as Navigation }
            }
        }
    }
    
    func registerReduxModule() {
        register {
            DependencyModule {
                $0.register { ReduxKt.store }
            }
        }
    }
    
    func registerDatabaseModule() {
        register {
            DependencyModule {
                $0.register { RealmDatabase() as Database }
            }
        }
    }
    
    func registerUtilsModule() {
        register {
            DependencyModule {
                $0.register { SystemDeviceUtils() as DeviceUtils }
                $0.register { SimpleUrlDownloader() as UrlDownloader }
                $0.register { SystemCalendarUtils() as CalendarUtils }
                $0.register { ExtensionContextDataCapture() as RawDataCapture }
                $0.register { SwiftSoupHTMLDataProcess() as HTMLDataProcess }
                $0.register { WebViewProvider() as WebViewProvider }
                $0.register { WebViewContentResolver(provider: self.resolve()) as ContentResolver }
                $0.register { SystemDataProcess(downloader: self.resolve(),
                                                resolver: self.resolve(),
                                                dataProcess: self.resolve()) as RawDataProcess }
            }
        }
    }
    
    func registerDataSourceModule() {
        register {
            DependencyModule {
                $0.register { FeedKitItemDataSource() as FeedItemDataSource }
            }
        }
    }
    
    func registerReposModule() {
        register {
            DependencyModule {
                $0.register {
                    SharedBookmarkRepository(
                        imageBookmarkDAO: (self.resolve() as Database).getImageBookmarkDAO(),
                        textBookmarkDAO: (self.resolve() as Database).getTextBookmarkDAO(),
                        linkBookmarkDAO: (self.resolve() as Database).getLinkBookmarkDAO(),
                        feedBookmarkDAO: (self.resolve() as Database).getFeedBookmarkDAO()) as BookmarkRepository
                }
                $0.register {
                    SharedTopicsRepository(topicDAO: (self.resolve() as Database).getTopicDAO()) as TopicsRepository
                }
                $0.register {
                    SharedFeedItemRepository(dataSource: self.resolve()) as FeedItemRepository
                }
                $0.register {
                    SharedFeedsRepository(userFeedsDAO: (self.resolve() as Database).getFeedBookmarkDAO(),
                                          allFeedsDAO: DefaultFeedBookmarkDAO()) as FeedsRepository
                }
            }
        }
    }
    
    func registerViewModelsModule() {
        register {
            DependencyModule {
                $0.register {
                    SharedPreviewViewModel(store: self.resolve(),
                                           bookmarkRepository: self.resolve(),
                                           calendar: self.resolve(),
                                           capture: self.resolve(),
                                           process: self.resolve()) as PreviewViewModel
                }
                $0.register {
                    SharedBookmarksViewModel(store: self.resolve(),
                                             calendar: self.resolve(),
                                             bookmarkRepository: self.resolve()) as BookmarksViewModel
                }
                $0.register {
                    SharedTopicsViewModel(store: self.resolve(),
                                          calendar: self.resolve(),
                                          topicsRepository: self.resolve(),
                                          bookmarkRepository: self.resolve()) as TopicsViewModel
                }
                
                $0.register {
                    SharedAddTopicViewModel(store: self.resolve(),
                                            topicsRepository: self.resolve()) as AddTopicViewModel
                }
                
                $0.register {
                    SharedEditBookmarkViewModel(store: self.resolve(),
                                                calendar: self.resolve(),
                                                bookmarkRepository: self.resolve(),
                                                topicsRepository: self.resolve()) as EditBookmarkViewModel
                }
                
                $0.register {
                    SharedEditTopicViewModel(store: self.resolve(),
                                             topicsRepository: self.resolve()) as EditTopicViewModel
                }
                
                $0.register {
                    SharedFeedDetailViewModel(store: self.resolve(),
                                              feedsRepository: self.resolve(),
                                              bookmarkRepository: self.resolve(),
                                              feedItemRepository: self.resolve()) as FeedDetailViewModel
                }
                
                $0.register {
                    SharedDisplayViewModel(store: self.resolve(),
                                           bookmarkRepository: self.resolve(),
                                           calendarUtils: self.resolve(),
                                           htmlDataProcess: self.resolve()) as DisplayViewModel
                }
                
                $0.register {
                    SharedHubViewModel(store: self.resolve(),
                                       calendar: self.resolve(),
                                       bookmarkRepository: self.resolve()) as HubViewModel
                }
                
                $0.register {
                    SharedFeedsViewModel(store: self.resolve(),
                                         bookmarkRepository: self.resolve(),
                                         calendar: self.resolve()) as FeedsViewModel
                }
            }
        }
    }
}
