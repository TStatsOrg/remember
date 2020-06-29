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
    
    public init() {
        
    }
    
    public var body: some View {
        List(state.items, rowContent: { content in
            VStack {
                HStack(alignment: .center) {

                    WebImage(url: content.icon)
                        .placeholder(content: {
                            Image(systemName: "bookmark.fill")
                                .foregroundColor(AppColors.secondaryColor)
                        })
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                        .frame(width: 60, height: 60)
                        .background(AppColors.mainColor)
                        .clipped()

                    Button(action: {
                        self.navigation.showRSSDetail(rssId: content.id)
                        self.isShowingSheet = true
                    }) {
                        VStack(alignment: .leading) {
                            Text(content.title)
                                .font(.body)
                                .fontWeight(.bold)
                            Text(content.link)
                                .font(.caption)
                                .foregroundColor(.secondary)
                                .lineLimit(1)
                        }
                    }

                    Spacer()
                }
                Divider()
            }
        })
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
