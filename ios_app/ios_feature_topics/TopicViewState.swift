//
//  TopicViewState.swift
//  ios_feature_topics
//
//  Created by Gabriel Coman on 29/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared

struct TopicViewState: Identifiable {
    
    private let topic: TopicState
    
    init(state: TopicState) {
        topic = state
    }
    
    var id: Int32 {
        return topic.id
    }
    
    var name: String {
        return topic.name
    }
}
