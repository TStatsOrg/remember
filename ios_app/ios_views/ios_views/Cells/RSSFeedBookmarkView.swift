//
//  RSSFeedBookmarkView.swift
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

public struct RSSFeedBookmarkView: View {
    
    private let viewState: BookmarkFeedViewState
    
    public init(viewState: BookmarkFeedViewState) {
        self.viewState = viewState
    }
    
    public var body: some View {
        VStack {
            HStack(alignment: .center) {

                WebImage(url: viewState.icon)
                    .placeholder(content: {
                        Image(systemName: "bookmark.fill")
                            .foregroundColor(AppColors.secondaryColor)
                    })
                    .resizable()
                    .aspectRatio(contentMode: .fill)
                    .frame(width: 60, height: 60)
                    .background(AppColors.mainColor)
                    .clipped()

                VStack(alignment: .leading) {
                    Text(viewState.title)
                        .font(.body)
                        .fontWeight(.bold)
                    BookmarkInfoView(viewState: viewState)
                }
                Spacer()
            }
            Divider()
        }
    }
}
