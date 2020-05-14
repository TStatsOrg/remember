//
//  BookmarkInfoView.swift
//  ios_feature_hub
//
//  Created by Gabriel Coman on 14/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import ios_dependencies
import RememberShared
import SDWebImageSwiftUI
import ios_views

struct BookmarkInfoView: View {
    
    let viewState: BookmarkViewStateType
    
    var body: some View {
        Text("\(viewState.source) - \(viewState.date)")
            .font(.caption)
            .foregroundColor(.secondary)
            .padding(EdgeInsets(top: 8, leading: 0, bottom: 16, trailing: 0))
    }
}
