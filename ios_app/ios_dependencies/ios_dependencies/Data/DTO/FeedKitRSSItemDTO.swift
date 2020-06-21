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

class FeedKitRSSItemDTO: RSSItemDTO {
    var caption: String? = ""
    var id: Int32 = 0
    var link: String = ""
    var pubDate: String = ""
    var title: String = ""
    
    public init(feedKit item: FeedKit.RSSFeedItem) {
        self.title = item.title ?? ""
//        self.pubDate = item.pubDate
        self.caption = item.description
        self.link = item.link ?? ""
        self.id = Int32(item.title?.count ?? 0)
    }
}
