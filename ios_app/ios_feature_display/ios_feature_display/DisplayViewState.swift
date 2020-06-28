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
    
    public init(state: DisplayState = DisplayState(item: nil, isBookmarked: false)) {
        self.state = state
    }
    
    public var url: URL? {
        if let url = self.state.item?.link {
            return URL(string: url)
        } else {
            return nil
        }
    }
    
    public var isBookmarked: Bool {
        return self.state.isBookmarked
    }
}
