//
//  SelectedTopicViewState.swift
//  ios_feature_bookmark_edit
//
//  Created by Gabriel Coman on 02/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared

struct SelectedTopicViewState: Identifiable {
    
    private let topic: TopicState
    
    let isSelected: Bool
    
    init(state: TopicState, isSelected selected: Bool) {
        topic = state
        isSelected = selected
    }
    
    var id: Int32 {
        return topic.id
    }
    
    var name: String {
        return topic.name
    }
}
