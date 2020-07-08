//
//  FeedViewState.swift
//  ios_feature_feed
//
//  Created by Gabriel Coman on 29/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import ios_views
import RememberShared

public struct UserFeedsViewState {
    
    private let state: BookmarksState?
    
    public init(state: BookmarksState? = nil) {
        self.state = state
    }
    
    private var bookmarks: [BookmarkState] {
        return state?.bookmarks.filter { $0.isFavourite } ?? []
    }
    
    var items: [BookmarkFeedViewState] {
        return bookmarks.map(BookmarkViewState.init)
            .compactMap { $0.viewState }
            .compactMap { $0 as? BookmarkFeedViewState }
    }
    
    public var empty: EmptyViewState {
        return EmptyViewState(isVisible: bookmarks.isEmpty,
                              image: "bookmark.fill",
                              text: Translations.UserFeeds.startupMessage)
    }
}
