//
//  FeedViewState.swift
//  ios_feature_feed
//
//  Created by Gabriel Coman on 29/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import ios_views
import RememberShared

public struct FeedViewState {
    
    private let state: [RSSFeedState]
    
    public init(state: [RSSFeedState] = []) {
        self.state = state
    }
    
    public var items: [RSSViewState] {
        return state.map(RSSViewState.init)
    }
}
