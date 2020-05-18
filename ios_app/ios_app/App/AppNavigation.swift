//
//  MainAppNavigation.swift
//  ios_app
//
//  Created by Gabriel Coman on 29/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import ios_dependencies
import ios_feature_topics
import ios_feature_topic_add
import ios_feature_bookmark_edit

class AppNavigation: Navigation {
    
    private var destination: NavigationDestination = .None
    
    func showTopicList() {
        destination = .TopicList(view: seeTopicsList())
    }
    
    func showEditBookmark(bookmarkId: Int32) {
        destination = .EditBookmark(view: seeEditBookmark(forBookmarkId: bookmarkId))
    }
    
    func showAddTopic() {
        destination = .AddTopic(view: seeAddTopic())
    }
    
    func showUrl(url: URL?) {
        if let url = url {
            UIApplication.shared.open(url)
        }
    }
    
    func content() -> AnyView {
        switch destination {
        case .TopicList(let view):
            return view
        case .EditBookmark(let view):
            return view
        case .AddTopic(let view):
            return view
        default:
            return AnyView(EmptyView())
        }
    }
    
    private func seeAddTopic() -> AnyView {
        return AnyView(NavigationView { AddTopicView() })
    }
    
    private func seeTopicsList() -> AnyView {
        return AnyView(NavigationView { TopicsView() })
    }
    
    private func seeEditBookmark(forBookmarkId id: Int32) -> AnyView {
        return AnyView(NavigationView { EditBookmarkView(bookmarkId: id) })
    }
}
