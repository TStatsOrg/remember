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
    var noBookmarks: Int { get }
}

public extension TopicViewStateType {
    
    var id: Int32 {
        return topic.id
    }
    
    var name: String {
        return topic.name.uppercased()
    }
    
    var isEditable: Bool {
        return topic.isEditable
    }
}

public class TopicViewState {
    
    public struct Normal: TopicViewStateType {
        public let topic: TopicState
        public let noBookmarks: Int
         
        
        public init(state: TopicState, noBookmarks: Int) {
            self.topic = state
            self.noBookmarks = noBookmarks
        }
    }
    
    public struct Selectable: TopicViewStateType {
        public let topic: TopicState
        public let isSelected: Bool
        public let noBookmarks: Int
        
        public init(state: TopicState, isSelected: Bool, noBookmarks: Int) {
            self.topic = state
            self.isSelected = isSelected
            self.noBookmarks = noBookmarks
        }
    }
}
