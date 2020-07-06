//
//  ImageBookmarkView.swift
//  ios_feature_hub
//
//  Created by Gabriel Coman on 30/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import ios_dependencies
import RememberShared
import SDWebImageSwiftUI

public struct ImageBookmarkView: View {
    
    private let viewState: BookmarkImageViewState
    
    public init(viewState: BookmarkImageViewState) {
        self.viewState = viewState
    }
    
    public var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            BookmarkTopicView(viewState: viewState)
            WebImage(url: self.viewState.url)
                .resizable()
                .scaledToFit()
                .paddingZero()
            BookmarkInfoView(viewState: viewState)
            Divider()
        }
        .paddingZero()
        .onTapGesture {
            /* N/A */
        }
    }
}
