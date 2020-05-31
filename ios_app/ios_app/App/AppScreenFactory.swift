//
//  AppScreenFactory.swift
//  ios_app
//
//  Created by Gabriel Coman on 18/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import ios_views
import ios_feature_topic_add
import ios_feature_bookmark_edit
import ios_feature_hub
import ios_feature_topics
import ios_feature_topic_edit

struct AppScreenFactory: ScreenFactory {
    
    func buildMainHubScreen() -> AnyView {
        return AnyView(NavigationView { MainHubView() }.navigationViewStyle(StackNavigationViewStyle()))
    }
    
    func buildTopicsListScreen() -> AnyView {
        return AnyView(NavigationView { TopicsView() }.navigationViewStyle(StackNavigationViewStyle()))
    }
    
    func buildEditBookmarkScreen(bookmarkId: Int32) -> AnyView {
        return AnyView(NavigationView { EditBookmarkView(bookmarkId: bookmarkId) }.navigationViewStyle(StackNavigationViewStyle()))
    }
    
    func buildAddTopicScreen() -> AnyView {
        return AnyView(NavigationView { AddTopicView() }.navigationViewStyle(StackNavigationViewStyle()))
    }
    
    func buildEditTopicScreen(topicId: Int32) -> AnyView {
        return AnyView(NavigationView { EditTopicView(topicId: topicId) }.navigationViewStyle(StackNavigationViewStyle()))
    }
}
