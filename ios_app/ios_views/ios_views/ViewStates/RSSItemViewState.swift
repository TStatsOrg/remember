//
//  RSSItemViewState.swift
//  ios_views
//
//  Created by Gabriel Coman on 21/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared

public struct RSSItemViewState: Identifiable {
    
    private let state: RSSFeedItemState
    
    public init(state: RSSFeedItemState) {
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
    
    public var date: String {
        return state.pubDate
    }
    
    public var caption: String? {
        return state.caption
    }
}
