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
    
    func seeAddTopic() -> AnyView {
        return AnyView(AddTopicView())
    }
    
    func seeTopicsList() -> AnyView {
        return AnyView(TopicsView())
    }
    
    func seeEditBookmark(forBookmarkId id: Int32) -> AnyView {
        return AnyView(EditBookmarkView(bookmarkId: id))
    }
}
