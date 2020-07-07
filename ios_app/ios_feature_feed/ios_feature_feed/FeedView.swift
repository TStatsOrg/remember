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
            List(state.bookmarksViewState, rowContent: getCellType)
        }
        .navigationBarTitle(Text(Translations.Feed.title), displayMode: .inline)
        .navigationBarItems(trailing: Button(action: {
            self.navigation.showRSS()
            self.isShowingSheet = true
        }, label: {
            Text(Translations.Feed.feedButtonTitle)
        }))
        .onAppear {
            self.viewModel.observeBookmarkState {
                self.state = FeedViewState(state: $0)
            }
            
            self.viewModel.loadData()
        }
        .onDisappear {
            // self.viewModel.cleanup()
        }
        .onReceive(NotificationCenter.default.publisher(for: UIApplication.willEnterForegroundNotification)) { _ in
            self.viewModel.loadData()
        }
        .sheet(isPresented: $isShowingSheet, content: navigation.content)
    }
    
    private func getCellType(state: BookmarkViewState) -> AnyView {
        guard let viewState = state.viewState else {
            return AnyView(Text("N/A"))
        }
        
        switch viewState {
        case let rssFeed as BookmarkRSSFeedViewState:
            return AnyView(
                RSSFeedBookmarkView(viewState: rssFeed)
                .onTapGesture {
                    self.navigation.showRSSDetail(rssId: rssFeed.id)
                    self.isShowingSheet = true
                }
            )
        default:
            return AnyView(Text("N/A"))
        }
    }
}
