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
    
    @Injected private var viewModel: FeedsViewModel
    @Injected private var navigation: Navigation
    @State private var state: AllFeedsViewState = AllFeedsViewState()
    @State private var isShowingSheet: Bool = false
    
    public init() {}
    
    public var body: some View {
        List(state.items, rowContent: buildRow)
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
    
    private func buildRow(state: BookmarkFeedViewState) -> some View {
        VStack {
            HStack(alignment: .center) {
                FeedBookmarkView(viewState: state)
                Spacer()
                ManagedSubscribeButton(isSubscribed: state.isSubscribed)
            }
            Divider()
        }
        .onTapGesture {
            self.viewModel.updateLatestDate(bookmarkId: state.id)
            self.navigation.showFeedDetail(bookmarkId: state.id)
            self.isShowingSheet = true
        }
    }
}
