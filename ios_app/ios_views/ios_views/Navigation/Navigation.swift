//
//  AppNavigation.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 29/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI

public enum NavigationDestination {
    case none
    case bookmarks(view: AnyView)
    case userFeeds(view: AnyView)
    case topicList(view: AnyView)
    case addTopic(view: AnyView)
    case editBookmark(view: AnyView)
    case editTopic(view: AnyView)
    case allFeeds(view: AnyView)
    case feedDetail(view: AnyView)
    case display(view: AnyView)
    case urlDestination
}

public protocol Navigation {
    
    func showFeedDetail(bookmarkId: Int32)
    func showAllFeeds()
    func showBookmarks()
    func showUserFeeds()
    func showTopicList()
    func showEditBookmark(bookmarkId: Int32)
    func showAddTopic()
    func showUrl(url: URL?)
    func showEditTopic(topicId: Int32)
    func showDisplay(url: URL?)
    
    func content() -> AnyView
}
