//
//  AppNavigation.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 29/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI

public enum NavigationDestination {
    case None
    case TopicList(view: AnyView)
    case AddTopic(view: AnyView)
    case EditBookmark(view: AnyView)
    case UrlDestination
}

public protocol Navigation {
    
    func showTopicList()
    func showEditBookmark(bookmarkId: Int32)
    func showAddTopic()
    func showUrl(url: URL?)
    
    func content() -> AnyView
}
