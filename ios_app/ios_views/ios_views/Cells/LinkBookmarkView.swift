//
//  LinkBookmarkView.swift
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

public struct LinkBookmarkView: View {
    
    private let viewState: BookmarkLinkViewState
    
    public init(viewState: BookmarkLinkViewState) {
        self.viewState = viewState
    }
    
    public var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            BookmarkTopicView(viewState: viewState)
            HStack(alignment: .top, spacing: 0) {
                
                Text(viewState.title)
                    .font(.body)
                    .fontWeight(.bold)
                    .lineLimit(nil)
                    .multilineTextAlignment(.leading)
                    .fixedSize(horizontal: false, vertical: true)
                
                if !self.viewState.isIconHidden {
                    Spacer()
                    WebImage(url: self.viewState.icon)
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                        .frame(width: 60, height: 60)
                        .clipped()
                }
            }
            BookmarkInfoView(viewState: viewState)
            Divider()
        }
    }
}
