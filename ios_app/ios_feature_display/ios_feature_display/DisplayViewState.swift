//
//  DisplayViewState.swift
//  ios_feature_display
//
//  Created by Gabriel Coman on 28/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import ios_views

public struct DisplayViewState {
    
    private let state: DisplayState
    
    public init(state: DisplayState = DisplayState()) {
        self.state = state
    }
    
    private var bookmark: BookmarkLinkViewState? {
        if let item = self.state.item {
            return BookmarkLinkViewState(state: item)
        } else {
            return nil
        }
    }
    
    public var title: String {
        return bookmark?.title ?? "Loading ..."
    }
    
    public var isBookmarked: Bool {
        return self.state.isBookmarked
    }
    
    public var isDisabled: Bool {
        return self.state.isLoading
    }
}
