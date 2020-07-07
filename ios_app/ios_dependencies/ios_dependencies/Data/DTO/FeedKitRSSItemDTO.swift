//
//  FeedKitRSSItemDTO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 21/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import FeedKit

class FeedKitRSSItemDTO: FeedItemDTO {
    var caption: String? = ""
    var id: Int32 = 0
    var link: String = ""
    var title: String = ""
    var pubDate: Int64 = 0
    
    public init(feedKit item: FeedKit.RSSFeedItem) {
        self.title = item.title ?? ""
        
        let pubDate = Int64(item.pubDate?.timeIntervalSince1970 ?? 0) * 1000
        let now: Int64 = Int64(Date().timeIntervalSince1970) * 1000
        
        self.pubDate = pubDate == 0 ? now : pubDate
        self.caption = item.description
        self.link = item.link ?? ""
        self.id = IdProvider().fromString(string: self.link)
    }
}
