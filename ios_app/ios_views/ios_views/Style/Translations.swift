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
    
    public struct Feed {
        public static let title: LocalizedStringKey = "feed_title"
        public static let feedButtonTitle: LocalizedStringKey = "feed_button_title"
    }
    
    public struct RSS {
        public static let title: LocalizedStringKey = "rss_title"
        public static let subscribeTitle: LocalizedStringKey = "rss_subscribe_button_title"
        public static let unsubscribeTitle: LocalizedStringKey = "rss_unsubscribe_button_title"
    }
    
    public struct Errors {
        public static let Network: LocalizedStringKey = "error_network"
        public static let InvalidRSSFormat: LocalizedStringKey = "error_rss"
        public static let Database: LocalizedStringKey = "error_database"
        public static let InvalidURL: LocalizedStringKey = "error_url"
        public static let Other: LocalizedStringKey = "error_other"
    }
    
    public struct General {
        public static let NoResults: LocalizedStringKey = "general_no_results"
    }
    
    public struct Display {
        public static let doneTitle: LocalizedStringKey = "display_done_button_title"
    }
}
