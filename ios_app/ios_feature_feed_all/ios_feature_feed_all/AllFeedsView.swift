//
//  AllFeedsView.swift
//  ios_feature_feed_all
//
//  Created by Gabriel Coman on 17/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import ios_dependencies
import RememberShared
import ios_views
import SDWebImageSwiftUI

public struct AllFeedsView: View {
    
    @Injected private var viewModel: AllFeedsViewModel
    @Injected private var navigation: Navigation
    @State private var state: AllFeedsViewState = AllFeedsViewState()
    @State private var isShowingSheet: Bool = false
    
    public init() {}
    
    public var body: some View {
        List(state.items, rowContent: self.getCellType)
        .navigationBarTitle(Text(Translations.AllFeeds.title))
        .onAppear {
            self.viewModel.observeState {
                self.state = AllFeedsViewState(state: $0)
            }
            self.viewModel.loadData()
        }
        .onDisappear {
            self.viewModel.cleanup()
        }
        .sheet(isPresented: $isShowingSheet, content: navigation.content)
    }
    
    private func getCellType(state: BookmarkViewState) -> AnyView {
        guard let viewState = state.viewState else {
            return AnyView(Text("N/A"))
        }
        
        switch viewState {
        case let feed as BookmarkFeedViewState:
            return AnyView(
                HStack {
                    FeedBookmarkView(viewState: feed)
                    Spacer()
                    ManagedSubscribeButton(isSubscribed: feed.isSubscribed)
                }
                .onTapGesture {
                    self.navigation.showFeedDetail(bookmarkId: feed.id)
                    self.isShowingSheet = true
                }
            )
        default:
            return AnyView(Text("N/A"))
        }
    }
}
