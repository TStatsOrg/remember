//
//  FeedKitAtomItemDTO.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 29/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import FeedKit

class FeeddKitAtomItemDTO: RSSItemDTO {
    var caption: String? = ""
    var id: Int32 = 0
    var link: String = ""
    var title: String = ""
    var pubDate: Int64 = 0
    
    public init(feedKit item: FeedKit.AtomFeedEntry) {
        self.caption = item.content?.value ?? ""
        self.title = item.title ?? ""
        self.pubDate = Int64(item.updated?.timeIntervalSince1970 ?? 0) * 1000
        self.link = item.links?.first?.attributes?.href ?? ""
        self.id = IdProvider().fromString(string: self.link)
    }
}
