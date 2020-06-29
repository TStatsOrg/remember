//
//  RSSViewState.swift
//  ios_views
//
//  Created by Gabriel Coman on 17/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared

public struct RSSViewState: Identifiable {
    
    private let state: RSSFeedState
    
    public init(state: RSSFeedState) {
        self.state = state
    }
    
    public var id: Int32 {
        return state.id
    }
    
    public var title: String {
        return state.title
    }
    
    public var link: String {
        return state.link
    }
    
    public var isSubscribed: Bool {
        return state.isSubscribed
    }
    
    public var icon: URL? {
        if let url = state.icon {
            return URL(string: url)
        } else {
            return nil
        }
    }
}
