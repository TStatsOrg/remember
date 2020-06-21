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
    
    func showRSSDetail(rssId: Int32) {
        destination = .RSSDetail(view: factory.buildRSSDetailScreen(rssId: rssId))
    }
    
    func showRSS() {
        destination = .RSS(view: factory.buildRSSScreen())
    }
    
    func showBookmarks() {
        destination = .Bookmarks(view: factory.buildBookmarksScreen())
    }
    
    func showFeed() {
        destination = .Feed(view: factory.buildFeedScreen())
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
    
    func showUrl(url: URL?) {
        if let url = url {
            UIApplication.shared.open(url)
        }
    }
    
    func content() -> AnyView {
        switch destination {
        case .RSS(let view):
            return view
        case .Feed(let view):
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
        case .RSSDetail(let view):
            return view
        default:
            return AnyView(EmptyView())
        }
    }
}
