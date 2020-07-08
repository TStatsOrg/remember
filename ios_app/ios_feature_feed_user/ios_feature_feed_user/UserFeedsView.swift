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

public struct UserFeedsView: View {
    
    @Injected private var navigation: Navigation
    @Injected private var viewModel: FeedsViewModel
    @State private var isShowingSheet: Bool = false
    @State private var state: UserFeedsViewState = UserFeedsViewState()
    
    public init() { /* n/a */ }
    
    public var body: some View {
        VStack {
            ManagedEmptyView(state: state.empty)
            List(state.items, rowContent: buildRow)
        }
        .navigationBarTitle(Text(Translations.UserFeeds.title), displayMode: .inline)
        .navigationBarItems(trailing: Button(action: {
            self.navigation.showAllFeeds()
            self.isShowingSheet = true
        }, label: {
            Text(Translations.UserFeeds.feedButtonTitle)
        }))
        .onAppear {
            self.viewModel.observeState {
                self.state = UserFeedsViewState(state: $0)
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
    
    private func buildRow(state: BookmarkFeedViewState) -> some View {
        VStack {
            FeedBookmarkView(viewState: state)
            Divider()
        }
        .onTapGesture {
            self.navigation.showFeedDetail(bookmarkId: state.id)
            self.isShowingSheet = true
        }
    }
}
