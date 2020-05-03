//
//  PreviewViewStates.swift
//  ios_feature_preview
//
//  Created by Gabriel Coman on 03/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import RememberShared
import Foundation

struct PreviewsViewState {
    
    let state: BookmarkState
    
    init(state: BookmarkState) {
        self.state = state
    }
    
    var viewStates: [PreviewViewState] {
        return [PreviewViewState(state: state)]
    }
}
