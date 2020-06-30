//
//  DisplayViewState.swift
//  ios_feature_display
//
//  Created by Gabriel Coman on 28/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared

public struct DisplayViewState {
    
    private let state: DisplayState
    
    public init(state: DisplayState = DisplayState()) {
        self.state = state
    }
    
    public var title: String {
        return self.state.title
    }
    
    public var url: URL? {
        return URL(string: self.state.url)
    }
    
    public var isBookmarked: Bool {
        return self.state.isBookmarked
    }
}
