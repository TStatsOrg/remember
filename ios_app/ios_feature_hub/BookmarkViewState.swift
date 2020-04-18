//
//  BookmarkState.swift
//  ios_feature_hub
//
//  Created by Gabriel Coman on 18/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared

struct BookmarkViewState: Identifiable {
    
    private let state: BookmarkState
    
    init(state: BookmarkState) {
        self.state = state
    }
    
    var id: Int32 {
        return state.id
    }
    
    var content: String {
        switch state {
        case let link as BookmarkStateLink:
            return link.url
        case let text as BookmarkStateText:
            return text.text
        case let image as BookmarkStateImage:
            return image.url
        default:
            return "N/A"
        }
    }
}
