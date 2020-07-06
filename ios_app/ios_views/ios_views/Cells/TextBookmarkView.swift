//
//  TextBookmarkView.swift
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

public struct TextBookmarkView: View {
    
    private let viewState: BookmarkTextViewState
    
    public init(viewState: BookmarkTextViewState) {
        self.viewState = viewState
    }
    
    public var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            BookmarkTopicView(viewState: viewState)
            Text(viewState.text)
                .fontWeight(.bold)
            BookmarkInfoView(viewState: viewState)
            Divider()
        }
        .onTapGesture {
            /* N/A */
        }
    }
}
