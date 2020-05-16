//
//  BoomarksViewState.swift
//  ios_feature_hub
//
//  Created by Gabriel Coman on 18/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import ios_views

struct BookmarksViewState {
    
    private let state: BookmarksState?
    
    init(state: BookmarksState? = nil) {
        self.state = state
    }
    
    private var bookmarks: [BookmarkState] {
        return state?.bookmarks ?? []
    }
    
    var noSearchResults: Bool {
        return bookmarks.isEmpty
    }
    
    var title: String {
        return "You have \(bookmarks.count) bookmarks"
    }
    
    var bookmarksViewState: [BookmarkViewState] {
        return bookmarks.map(BookmarkViewState.init)
    }
}
