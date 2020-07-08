//
//  MainAppNavigation.swift
//  ios_app
//
//  Created by Gabriel Coman on 29/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import ios_views
import ios_dependencies

class AppNavigation: Navigation {
    
    @Injected private var factory: ScreenFactory
    
    private var destination: NavigationDestination = .None
    
    func showFeedDetail(bookmarkId: Int32) {
        destination = .FeedDetail(view: factory.buildFeedDetailScreen(bookmarkId: bookmarkId))
    }
    
    func showAllFeeds() {
        destination = .AllFeeds(view: factory.buildAllFeedsScreen())
    }
    
    func showBookmarks() {
        destination = .Bookmarks(view: factory.buildBookmarksScreen())
    }
    
    func showUserFeeds() {
        destination = .UserFeeds(view: factory.buildUserFeedsScreen())
    }
    
    func showTopicList() {
        destination = .TopicList(view: factory.buildTopicsListScreen())
    }
    
    func showEditBookmark(bookmarkId: Int32) {
        destination = .EditBookmark(view: factory.buildEditBookmarkScreen(bookmarkId: bookmarkId))
    }
    
    func showAddTopic() {
        destination = .AddTopic(view: factory.buildAddTopicScreen())
    }
    
    func showEditTopic(topicId: Int32) {
        destination = .EditTopic(view: factory.buildEditTopicScreen(topicId: topicId))
    }
    
    func showDisplay(url: URL?) {
        destination = .Display(view: factory.buildDisplayView(url: url))
    }
    
    func showUrl(url: URL?) {
        if let url = url {
            UIApplication.shared.open(url)
        }
    }
    
    func content() -> AnyView {
        switch destination {
        case .AllFeeds(let view):
            return view
        case .UserFeeds(let view):
            return view
        case .Bookmarks(let view):
            return view
        case .TopicList(let view):
            return view
        case .EditBookmark(let view):
            return view
        case .AddTopic(let view):
            return view
        case .EditTopic(let view):
            return view
        case .FeedDetail(let view):
            return view
        case .Display(let view):
            return view
        default:
            return AnyView(EmptyView())
        }
    }
}
