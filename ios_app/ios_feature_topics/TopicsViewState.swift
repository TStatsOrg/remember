//
//  TopicsViewState.swift
//  ios_feature_topics
//
//  Created by Gabriel Coman on 29/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import ios_views
import RememberShared

struct TopicsViewState {
    private let state: [TopicState]
    
    init(state: [TopicState] = []) {
        self.state = state
    }
    
    var viewStates: [TopicViewState.Normal] {
        return state.map { TopicViewState.Normal(state: $0) }
    }
}
