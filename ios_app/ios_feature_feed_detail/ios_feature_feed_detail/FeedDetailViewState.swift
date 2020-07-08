//
//  FeedDetailViewState.swift
//  ios_feature_feed_detail
//
//  Created by Gabriel Coman on 21/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import ios_views

public struct FeedDetailViewState {
    
    private let state: FeedDetailState
    
    public init(state: FeedDetailState = FeedDetailState()) {
        self.state = state
    }
    
    private var feedState: BookmarkFeedViewState? {
        if let state = state.feedState {
            return BookmarkFeedViewState(state: state)
        } else {
            return nil
        }
    }
    
    public var items: [FeedItemViewState] {
        return state.items.map(FeedItemViewState.init)
    }
    
    public var title: String {
        return feedState?.title ?? "N/A"
    }
    
    public var isSubscribeButtonVisible: Bool {
        return !(feedState?.isSubscribed ?? false)
    }
    
    public var isUnsubscribeButtonVisible: Bool {
        return feedState?.isSubscribed ?? false
    }
    
    public var errorViewState: ErrorViewState {
        return ErrorViewState(error: state.error)
    }
    
    public var isContentVisible: Bool {
        return !errorViewState.isVisible
    }
    
    public var id: Int32 {
        return feedState?.id ?? 0
    }
    
    public var isSubscribed: Bool {
        return feedState?.isSubscribed ?? false
    }
}
