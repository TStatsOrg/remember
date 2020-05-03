//
//  EditBookmarkViewState.swift
//  ios_feature_bookmark_edit
//
//  Created by Gabriel Coman on 02/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared

struct EditBookmarkViewState {
    
    private let state: EditBookmarkState?
    
    init(state: EditBookmarkState? = nil) {
        self.state = state
    }
    
    var bookmarkId: Int32 {
        return state?.bookmark.id ?? 0
    }
    
    var viewStates: [SelectedTopicViewState] {
        return state?.topics.map { SelectedTopicViewState(state: $0, isSelected: $0.id == state?.bookmark.topic?.id) } ?? []
    }
}
