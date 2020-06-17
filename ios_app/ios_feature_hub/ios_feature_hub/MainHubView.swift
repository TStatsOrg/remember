//
//  MainHubView.swift
//  ios_feature_hub
//
//  Created by Gabriel Coman on 16/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import ios_views
import ios_dependencies
import ios_feature_bookmarks
import ios_feature_feed

struct FirstTab: View {

    @Injected private var screenFactory: ScreenFactory

    var body: some View {
        screenFactory.buildFeedScreen()
    }
}

struct SecondTab: View {

    @Injected private var screenFactory: ScreenFactory

    var body: some View {
        screenFactory.buildBookmarksScreen()
    }
}

public struct MainHubView: View  {

    @Injected private var screenFactory: ScreenFactory

    private let firstTabView = FirstTab()
    private let secondTabView = SecondTab()

    public init() { /* n/a */ }

    public var body: some View {
        TabView {
            NavigationView {
                FeedView()
            }
                .tabItem {
                    Image(systemName: "house.fill")
                    Text("Home")
                }
            NavigationView {
                BookmarksView()
            }.tabItem {
                    Image(systemName: "bookmark.fill")
                    Text("Bookmarks")
                }
        }
        .navigationBarHidden(true)
    }
}
