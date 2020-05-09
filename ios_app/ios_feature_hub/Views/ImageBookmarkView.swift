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
import ios_views

struct ImageBookmarkView: View {
    
    let viewState: BookmarkImageViewState
    
    var body: some View {
        VStack(alignment: .leading) {
            BookmarkTopicView(viewState: viewState)
            WebImage(url: self.viewState.url)
                .resizable()
                .frame(height: 300)
                .clipped()
            Text(viewState.date)
                .fontWeight(.light)
        }
        .onTapGesture {
            /* N/A */
        }
    }
}
