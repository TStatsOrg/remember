//
//  FeedView.swift
//  ios_feature_feed
//
//  Created by Gabriel Coman on 16/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import ios_views
import ios_dependencies
import RememberShared
import SDWebImageSwiftUI

public struct FeedView: View {
    
    @Injected private var navigation: Navigation
    @Injected private var viewModel: FeedViewModel
    @State private var isShowingSheet: Bool = false
    @State private var state: FeedViewState = FeedViewState()
    
    public init() { /* n/a */ }
    
    public var body: some View {
        VStack {
            ManagedEmptyView(state: state.empty)
            FeedListView(state: $state, buttonAction: { id in
                self.navigation.showRSSDetail(rssId: id)
                self.isShowingSheet = true
            })
        }
        .navigationBarTitle(Text(Translations.Feed.title), displayMode: .inline)
        .navigationBarItems(trailing: Button(action: {
            self.navigation.showRSS()
            self.isShowingSheet = true
        }, label: {
            Text(Translations.Feed.feedButtonTitle)
        }))
        .onAppear {
            self.viewModel.observeUserRSSFeed {
                self.state = FeedViewState(state: $0.feed)
            }
            self.viewModel.loadOwnRSSFeeds()
        }
        .onDisappear {
            // self.viewModel.cleanup()
        }
        .sheet(isPresented: $isShowingSheet, content: navigation.content)
    }
}
