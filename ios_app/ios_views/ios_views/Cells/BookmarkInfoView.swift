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

public struct BookmarkInfoView: View {
    
    private let viewState: BookmarkViewStateType
    
    public init(viewState: BookmarkViewStateType) {
        self.viewState = viewState
    }
    
    public var body: some View {
        Text(viewState.info)
            .font(.caption)
            .foregroundColor(.secondary)
            .lineLimit(1)
            .padding(EdgeInsets(top: 8, leading: 0, bottom: 16, trailing: 0))
    }
}
