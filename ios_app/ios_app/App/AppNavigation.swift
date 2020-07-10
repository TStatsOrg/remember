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
    
    private var destination: NavigationDestination = .none
    
    func showFeedDetail(bookmarkId: Int32) {
        destination = .feedDetail(view: factory.buildFeedDetailScreen(bookmarkId: bookmarkId))
    }
    
    func showAllFeeds() {
        destination = .allFeeds(view: factory.buildAllFeedsScreen())
    }
    
    func showBookmarks() {
        destination = .bookmarks(view: factory.buildBookmarksScreen())
    }
    
    func showUserFeeds() {
        destination = .userFeeds(view: factory.buildUserFeedsScreen())
    }
    
    func showTopicList() {
        destination = .topicList(view: factory.buildTopicsListScreen())
    }
    
    func showEditBookmark(bookmarkId: Int32) {
        destination = .editBookmark(view: factory.buildEditBookmarkScreen(bookmarkId: bookmarkId))
    }
    
    func showAddTopic() {
        destination = .addTopic(view: factory.buildAddTopicScreen())
    }
    
    func showEditTopic(topicId: Int32) {
        destination = .editTopic(view: factory.buildEditTopicScreen(topicId: topicId))
    }
    
    func showDisplay(url: URL?) {
        destination = .display(view: factory.buildDisplayView(url: url))
    }
    
    func showUrl(url: URL?) {
        if let url = url {
            UIApplication.shared.open(url)
        }
    }
    
    func content() -> AnyView {
        switch destination {
        case .allFeeds(let view):
            return view
        case .userFeeds(let view):
            return view
        case .bookmarks(let view):
            return view
        case .topicList(let view):
            return view
        case .editBookmark(let view):
            return view
        case .addTopic(let view):
            return view
        case .editTopic(let view):
            return view
        case .feedDetail(let view):
            return view
        case .display(let view):
            return view
        default:
            return AnyView(EmptyView())
        }
    }
}
