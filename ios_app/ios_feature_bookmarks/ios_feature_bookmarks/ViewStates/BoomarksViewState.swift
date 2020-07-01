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
    
    private var currentSearchText: String {
        return state?.searchTerm ?? searchTextWhenFilteringByTopic
    }
    
    var isFilteringByTopic: Bool {
        return state?.filterByTopic?.name != nil
    }
    
    var searchTextWhenFilteringByTopic: String {
        if let text = state?.filterByTopic?.name {
            return "in: \(text)"
        } else {
            return ""
        }
    }
    
    var title: String {
        return "You have \(bookmarks.count) bookmarks"
    }
    
    var bookmarksViewState: [BookmarkViewState] {
        return bookmarks.map(BookmarkViewState.init)
    }
    
    var empty: EmptyViewState {
        return EmptyViewState(isVisible: currentSearchText.isEmpty && bookmarks.isEmpty,
                              image: "bookmark.fill",
                              text: Translations.Bookmarks.startupMessage)
    }
    
    var noSearchResults: EmptyViewState {
        return EmptyViewState(isVisible: !currentSearchText.isEmpty && bookmarks.isEmpty,
                              image: "nosign",
                              text: Translations.General.NoResults)
    }
}
