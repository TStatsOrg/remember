//
//  BookmarkTopicView.swift
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

struct BookmarkTopicView: View {
    
    @Injected var navigation: Navigation
    
    let viewState: BookmarkViewStateType
    
    var body: some View {
        Text(viewState.topic)
            .font(.callout)
            .fontWeight(.semibold)
            .foregroundColor(.accentColor)
    }
}
