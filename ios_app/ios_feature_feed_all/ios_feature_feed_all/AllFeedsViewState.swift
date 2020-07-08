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
    
    private let state: BookmarksState?
    
    public init(state: BookmarksState? = nil) {
        self.state = state
    }
    
    private var bookmarks: [BookmarkState] {
        return state?.bookmarks ?? []
    }
    
    var items: [BookmarkFeedViewState] {
        return bookmarks.map(BookmarkViewState.init)
            .compactMap { $0.viewState }
            .compactMap { $0 as? BookmarkFeedViewState }
    }
}
