//
//  TopicsViewState.swift
//  ios_feature_topics
//
//  Created by Gabriel Coman on 29/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import ios_views
import RememberShared

struct TopicsViewState {
    private let state: [TopicState]
    private let bookmarks: [BookmarkState]
    
    init(
        state: [TopicState] = [],
        bookmarks: [BookmarkState] = []
    ) {
        self.state = state
        self.bookmarks = bookmarks
    }
    
    var viewStates: [TopicViewState.Normal] {
        return state.map {
            TopicViewState.Normal(
                state: $0,
                noBookmarks: self.getNoBookmarks(fromTopic: $0)
            )
        }
    }
    
    private func getNoBookmarks(fromTopic topic: TopicState) -> Int {
        return bookmarks.filter { $0.topic?.id == topic.id }.count
    }
}
