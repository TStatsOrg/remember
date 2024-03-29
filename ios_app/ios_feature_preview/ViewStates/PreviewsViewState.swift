//
//  PreviewViewStates.swift
//  ios_feature_preview
//
//  Created by Gabriel Coman on 03/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import RememberShared
import Foundation
import ios_views

struct PreviewsViewState {
    
    let state: PreviewState
    
    init(state: PreviewState) {
        self.state = state
    }
    
    var viewStates: [BookmarkViewState] {
        if let preview = state.preview {
            return [BookmarkViewState(state: preview)]
        } else {
            return []
        }
    }
    
    var isHidden: Bool {
        return !state.isLoading
    }
}
