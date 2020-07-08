//
//  Strings.swift
//  ios_views
//
//  Created by Gabriel Coman on 08/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI

public struct Translations {
    
    public struct Bookmarks {
        public static let startupMessage: LocalizedStringKey = "bookmarks_startup_message"
    }
    
    public struct EditBookmarks {
        
        public static let title: LocalizedStringKey = "bookmark_edit_title"
        public static let addButtonTitle: LocalizedStringKey = "bookmark_edit_add_topics"
    }
    
    public struct Topics {
        
        public static let title: LocalizedStringKey = "topics_title"
        public static let addButtonTitle: LocalizedStringKey = "topics_add"
    }
    
    public struct MainHub {
        public static let tab1Title: LocalizedStringKey = "main_hub_tab_1"
        public static let tab2Title: LocalizedStringKey = "main_hub_tab_2"
    }
    
    public struct UserFeeds {
        public static let title: LocalizedStringKey = "user_feed_title"
        public static let feedButtonTitle: LocalizedStringKey = "user_feed_button_title"
        public static let startupMessage: LocalizedStringKey = "user_feed_startup_message"
    }
    
    public struct AllFeeds {
        public static let title: LocalizedStringKey = "all_feeds_title"
        public static let subscribeTitle: LocalizedStringKey = "all_feeds_subscribe_button_title"
        public static let unsubscribeTitle: LocalizedStringKey = "all_feeds_unsubscribe_button_title"
    }
    
    public struct Errors {
        public static let Network: LocalizedStringKey = "error_network"
        public static let InvalidFeedFormat: LocalizedStringKey = "error_feed"
        public static let Database: LocalizedStringKey = "error_database"
        public static let InvalidURL: LocalizedStringKey = "error_url"
        public static let Other: LocalizedStringKey = "error_other"
    }
    
    public struct General {
        public static let NoResults: LocalizedStringKey = "general_no_results"
    }
    
    public struct Display {
        public static let title: LocalizedStringKey = "display_title"
    }
}
