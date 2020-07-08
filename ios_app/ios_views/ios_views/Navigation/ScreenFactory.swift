//
//  ScreenFactory.swift
//  ios_views
//
//  Created by Gabriel Coman on 18/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI

public protocol ScreenFactory {
    func buildFeedDetailScreen(bookmarkId: Int32) -> AnyView
    func buildAllFeedsScreen() -> AnyView
    func buildMainHubScreen() -> AnyView
    func buildUserFeedsScreen() -> AnyView
    func buildBookmarksScreen() -> AnyView
    func buildTopicsListScreen() -> AnyView
    func buildEditBookmarkScreen(bookmarkId: Int32) -> AnyView
    func buildAddTopicScreen() -> AnyView
    func buildEditTopicScreen(topicId: Int32) -> AnyView
    func buildDisplayView(url: URL?) -> AnyView
}
