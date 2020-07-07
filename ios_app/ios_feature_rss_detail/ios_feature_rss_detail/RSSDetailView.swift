//
//  RSSItemsView.swift
//  ios_feature_rss_items
//
//  Created by Gabriel Coman on 21/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import ios_dependencies
import ios_views
import RememberShared

public struct RSSDetailView: View {
    
    @Injected private var viewModel: RSSDetailViewModel
    @Injected private var navigation: Navigation
    @State private var state: RSSDetailViewState = RSSDetailViewState()
    @State private var isShowingSheet: Bool = false
    
    private let rssId: Int32
    
    public init(rssId: Int32) {
        self.rssId = rssId
    }
    
    public var body: some View {
        VStack(alignment: .center) {
            RSSFeedItems()
        }
        .navigationBarTitle(Text(state.title), displayMode: .inline)
        .navigationBarItems(trailing:
            ManagedSubscribeButton(isSubscribed: self.state.isSubscribed,
                                   subscribeAction: { self.viewModel.save(bookmarkId: self.state.id) },
                                   unsubscribeAction: { self.viewModel.delete(bookmarkId: self.state.id) })
        )
        .onAppear {
            self.viewModel.observeRSSDetailsState {
                self.state = RSSDetailViewState(state: $0)
            }
            self.viewModel.loadFeedItems(bookmarkId: self.rssId)
        }
        .onDisappear {
            self.viewModel.cleanup()
        }
        .sheet(isPresented: $isShowingSheet, content: navigation.content)
    }
    
    private func RSSFeedItems() -> some View {
        HStack {
            if state.isContentVisible {
                List(state.items, rowContent: { content in
                    VStack(alignment: .leading, spacing: 0) {
                        Text(content.title)
                            .fontWeight(.bold)
                        Text(content.date)
                            .font(.caption)
                            .foregroundColor(.secondary)
                            .lineLimit(1)
                            .padding(EdgeInsets(top: 8, leading: 0, bottom: 16, trailing: 0))
                        Divider()
                    }
                    .onTapGesture {
                        self.navigation.showDisplay(url: content.url)
                        self.isShowingSheet = true
                    }
                })
            } else {
                ErrorView(error: state.errorViewState)
            }
        }
        .frame(
            maxWidth: .infinity,
            maxHeight: .infinity,
            alignment: .center
        )
        
    }
}
