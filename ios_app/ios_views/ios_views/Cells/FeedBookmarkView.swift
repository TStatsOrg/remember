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
        HStack(alignment: .center) {

            WebImage(url: viewState.icon)
                .placeholder(content: {
                    Image(systemName: "bookmark.fill")
                        .frame(width: 60, height: 60)
                        .foregroundColor(AppColors.secondaryColor)
                        .background(AppColors.mainColor)
                })
                .resizable()
                .aspectRatio(contentMode: .fill)
                .frame(width: 60, height: 60)
                .clipped()

            VStack(alignment: .leading) {
                Text(viewState.title)
                    .font(.body)
                    .fontWeight(.bold)
                BookmarkInfoView(viewState: viewState)
            }
            Spacer()
        }
    }
}
