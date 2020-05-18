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
    case AddTopic
    case EditBookmark(view: AnyView)
    case UrlDestination
}

public protocol Navigation {
    
    var destination: NavigationDestination { get set }
    var isNavigating: State<Bool> { get }
    
    func showTopicList()
    func showEditBookmark(bookmarkId: Int32)
    
    func seeTopicsList() -> AnyView
    func seeAddTopic() -> AnyView
    func seeEditBookmark(forBookmarkId id: Int32) -> AnyView
    func seeUrlDestination(url: URL?)
}
