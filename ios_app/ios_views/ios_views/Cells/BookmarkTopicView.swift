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

public struct BookmarkTopicView: View {
    
    private let viewState: BookmarkViewStateType
    
    public init(viewState: BookmarkViewStateType) {
        self.viewState = viewState
    }
    
    public var body: some View {
        Text(viewState.topic)
            .ActionButton()
            .padding(EdgeInsets(top: 0, leading: 0, bottom: 10, trailing: 0))
        
    }
}
