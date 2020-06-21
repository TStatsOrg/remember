//
//  RSSDetailViewState.swift
//  ios_feature_rss_detail
//
//  Created by Gabriel Coman on 21/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import ios_views

public struct RSSDetailViewState {
    
    private let state: RSSFeedDetailState
    
    public init(state: RSSFeedDetailState = RSSFeedDetailState()) {
        self.state = state
    }
    
    private var feedState: RSSFeedState {
        return state.feedState
    }
    
    public var items: [RSSItemViewState] {
        return state.items.map(RSSItemViewState.init)
    }
    
    public var title: String {
        return feedState.title
    }
    
    public var isSubscribeButtonVisible: Bool {
        return !feedState.isSubscribed
    }
    
    public var isUnsubscribeButtonVisible: Bool {
        return feedState.isSubscribed
    }
    
    public var errorViewState: ErrorViewState {
        return ErrorViewState(error: state.error)
    }
    
    public var isContentVisible: Bool {
        return !errorViewState.isVisible
    }
}
