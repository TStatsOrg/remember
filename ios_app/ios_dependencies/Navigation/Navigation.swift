//
//  AppNavigation.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 29/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI

public protocol Navigation {
    func seeTopicsList() -> AnyView
    func seeAddTopic() -> AnyView
    func seeEditBookmark(forBookmarkId id: Int32) -> AnyView
    func seeUrlDestination(url: URL?)
}
