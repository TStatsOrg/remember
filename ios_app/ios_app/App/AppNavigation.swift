//
//  MainAppNavigation.swift
//  ios_app
//
//  Created by Gabriel Coman on 29/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import ios_dependencies
import ios_feature_topics

class AppNavigation: Navigation {
    func seeTopicsList() -> AnyView {
        return AnyView(TopicsView())
    }
}
