//
//  FeedDetailView.swift
//  ios_feature_feed_detail
//
//  Created by Gabriel Coman on 21/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import ios_dependencies
import ios_views
import RememberShared

public struct FeedDetailView: View {
    
    @Injected private var viewModel: FeedDetailViewModel
    @Injected private var navigation: Navigation
    @State private var state: FeedDetailViewState = FeedDetailViewState()
    @State private var isShowingSheet: Bool = false
    
    private let bookmarkId: Int32
    
    public init(bookmarkId: Int32) {
        self.bookmarkId = bookmarkId
    }
    
    public var body: some View {
        VStack(alignment: .center) {
            MainView()
        }
        .navigationBarTitle(Text(state.title), displayMode: .inline)
        .navigationBarItems(trailing:
            ManagedSubscribeButton(isSubscribed: self.state.isSubscribed,
                                   subscribeAction: { self.viewModel.subscribe(bookmarkId: self.state.id) },
                                   unsubscribeAction: { self.viewModel.unsubscribe(bookmarkId: self.state.id) })
        )
        .onAppear {
            self.viewModel.observeState {
                self.state = FeedDetailViewState(state: $0)
            }
            self.viewModel.loadFeedItems(bookmarkId: self.bookmarkId)
        }
        .onDisappear {
            self.viewModel.cleanup()
        }
        .sheet(isPresented: $isShowingSheet, content: navigation.content)
    }
    
    private func MainView() -> some View {
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
