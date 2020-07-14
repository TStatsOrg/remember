//
//  FeedBookmarkView.swift
//  ios_feature_bookmarks
//
//  Created by Gabriel Coman on 06/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import ios_dependencies
import RememberShared
import SDWebImageSwiftUI

public struct FeedBookmarkView: View {
    
    private let viewState: BookmarkFeedViewState
    
    public init(viewState: BookmarkFeedViewState) {
        self.viewState = viewState
    }
    
    public var body: some View {
        HStack(alignment: .top) {

            WebImage(url: viewState.icon)
                .placeholder(content: {
                    Image(systemName: "bookmark.fill")
                        .frame(width: 36, height: 36)
                        .foregroundColor(AppColors.secondaryColor)
                        .background(AppColors.mainColor)
                })
                .resizable()
                .aspectRatio(contentMode: .fill)
                .frame(width: 36, height: 36)
                .clipped()
                .padding(trailing: 8.0)

            VStack(alignment: .leading) {
                Text(viewState.title)
                    .font(.body)
                    .fontWeight(.bold)
                    .foregroundColor(viewState.titleColor)
                Text(viewState.info)
                    .font(.caption)
                    .foregroundColor(.secondary)
                    .lineLimit(1)
                    .padding(top: 4.0)
                    .padding(bottom: 4.0)
            }
            Spacer()
        }
    }
}
