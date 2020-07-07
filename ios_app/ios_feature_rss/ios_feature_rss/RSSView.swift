//
//  RSSView.swift
//  ios_feature_rss
//
//  Created by Gabriel Coman on 17/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import ios_dependencies
import RememberShared
import ios_views
import SDWebImageSwiftUI

public struct RSSView: View {
    
    @Injected private var viewModel: RSSViewModel
    @Injected private var navigation: Navigation
    @State private var state: RSSListViewState = RSSListViewState()
    @State private var isShowingSheet: Bool = false
    
    public init() {}
    
    public var body: some View {
        List(state.items, rowContent: { content in
            self.getCellType(state: content)
//            VStack {
//                HStack(alignment: .center) {
//
//                    WebImage(url: content.icon)
//                        .placeholder(content: {
//                            Image(systemName: "bookmark.fill")
//                                .foregroundColor(AppColors.secondaryColor)
//                        })
//                        .resizable()
//                        .aspectRatio(contentMode: .fill)
//                        .frame(width: 60, height: 60)
//                        .background(AppColors.mainColor)
//                        .clipped()
//
//                    Button(action: {
//                        self.navigation.showRSSDetail(rssId: content.id)
//                        self.isShowingSheet = true
//                    }) {
//                        VStack(alignment: .leading) {
//                            Text(content.title)
//                                .font(.body)
//                                .fontWeight(.bold)
//                            Text(content.link)
//                                .font(.caption)
//                                .foregroundColor(.secondary)
//                                .lineLimit(1)
//                        }
//                    }
//
//                    Spacer()
//                    ManagedSubscribeButton(isSubscribed: content.isSubscribed)
//                }
//                Divider()
//            }
        })
        .navigationBarTitle(Text(Translations.RSS.title))
        .onAppear {
            self.viewModel.observeRSSState {
                self.state = RSSListViewState(state: $0)
            }
            self.viewModel.loadRSSFeeds()
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
        case let rssFeed as BookmarkRSSFeedViewState:
            return AnyView(RSSFeedBookmarkView(viewState: rssFeed))
        default:
            return AnyView(Text("N/A"))
        }
    }
}
