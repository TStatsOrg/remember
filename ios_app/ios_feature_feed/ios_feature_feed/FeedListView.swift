//
//  FeedListView.swift
//  ios_feature_feed
//
//  Created by Gabriel Coman on 01/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import ios_views
import ios_dependencies
import RememberShared
import SDWebImageSwiftUI

public struct FeedListView: View {
    
    @Injected private var navigation: Navigation
    @Binding private var state: FeedViewState
    private let buttonAction: (Int32) -> Void
    
    public init(state: Binding<FeedViewState>, buttonAction: @escaping (Int32) -> Void) {
        self._state = state
        self.buttonAction = buttonAction
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

                    Button(action: { self.buttonAction(content.id) }) {
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
            .background(Color.red)
    }
}
