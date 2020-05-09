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
import ios_views

struct LinkBookmarkView: View {
    
    @Injected var navigation: Navigation
    let viewState: BookmarkLinkViewState
    
    var body: some View {
        VStack(alignment: .leading) {
            BookmarkTopicView(viewState: viewState)
            HStack(alignment: .top) {
                Text(viewState.title ?? "")
                    .fontWeight(.bold)
                    .lineLimit(nil)
                if !self.viewState.isIconHidden {
                    WebImage(url: self.viewState.icon)
                        .resizable()
                        .frame(width: 60, height: 60)
                        .scaledToFit()
                        .clipped()
                }
            }
            .background(Button(action: {
                self.navigation.seeUrlDestination(url: self.viewState.destinationUrl)
            }, label: { EmptyView() }))
            Text(viewState.date)
                .fontWeight(.light)
        }
    }
}
