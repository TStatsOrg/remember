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
    @State private var state: RSSDetailViewState = RSSDetailViewState()
    
    private let rssId: Int32
    
    public init(rssId: Int32) {
        self.rssId = rssId
    }
    
    public var body: some View {
        VStack(alignment: .center) {
            RSSFeedItems()
        }
        .navigationBarTitle(Text(state.title), displayMode: .inline)
        .onAppear {
            self.viewModel.observeRSSDetailsState {
                self.state = RSSDetailViewState(state: $0)
            }
            self.viewModel.loadRSSFeedData(rssId: self.rssId)
        }
        .onDisappear {
            self.viewModel.cleanup()
        }
    }
    
    private func RSSFeedItems() -> some View {
        HStack {
            if state.isContentVisible {
                List(state.items, rowContent: { content in
                    VStack(alignment: .leading, spacing: 0) {
                        Text(content.title)
                            .fontWeight(.bold)
                        Text(content.link)
                            .font(.caption)
                            .foregroundColor(.secondary)
                            .lineLimit(1)
                            .padding(EdgeInsets(top: 8, leading: 0, bottom: 16, trailing: 0))
                        Divider()
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
