//
//  AllFeedsViewState.swift
//  ios_feature_feed_all
//
//  Created by Gabriel Coman on 17/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import ios_views
import RememberShared

public struct AllFeedsViewState {
    
    private let state: [BookmarkState]
    
    public init(state: [BookmarkState] = []) {
        self.state = state
    }
    
    var items: [BookmarkFeedViewState] {
        return state.map(BookmarkViewState.init)
            .compactMap { $0.viewState }
            .compactMap { $0 as? BookmarkFeedViewState }
    }
}
