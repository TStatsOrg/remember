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
    private let state: [BookmarkState]
    
    init(state: [BookmarkState] = []) {
        self.state = state
    }
    
    var title: String {
        return "You have \(state.count) bookmarks"
    }
    
    var viewStates: [BookmarkViewState] {
        return state.map { BookmarkViewState(state: $0) }
    }
}
