//
//  TopicViewState.swift
//  ios_views
//
//  Created by Gabriel Coman on 05/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared

public protocol TopicViewStateType: Identifiable {
    var topic: TopicState { get }
}

public extension TopicViewStateType {
    
    var id: Int32 {
        return topic.id
    }
    
    var name: String {
        return topic.name
    }
}

public class TopicViewState {
    
    public struct Normal: TopicViewStateType {
        public let topic: TopicState
        
        public init(state: TopicState) {
            topic = state
        }
    }
    
    public struct Selectable: TopicViewStateType {
        public let topic: TopicState
        
        public let isSelected: Bool
        
        public init(state: TopicState, isSelected: Bool) {
            self.topic = state
            self.isSelected = isSelected
        }
    }
}
