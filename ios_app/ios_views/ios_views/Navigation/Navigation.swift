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
    case None
    case Bookmarks(view: AnyView)
    case UserFeeds(view: AnyView)
    case TopicList(view: AnyView)
    case AddTopic(view: AnyView)
    case EditBookmark(view: AnyView)
    case EditTopic(view: AnyView)
    case AllFeeds(view: AnyView)
    case FeedDetail(view: AnyView)
    case Display(view: AnyView)
    case UrlDestination
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
