//
//  FeedKitRSSItemDataSource.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 21/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import FeedKit

public class FeedKitRSSItemDataSource: RSSItemDataSource {
    public func getRSSItems(fromLink: String) -> [RSSItemDTO] {
        
        guard let url = URL(string: fromLink) else {
            return []
        }
        
        let parser = FeedParser(URL: url)
        
        let result = parser.parse()
        
        switch result {
        case .success(let feed):
            switch feed {
            case let .atom(items):       // Atom Syndication Format Feed Model
                return []
            case let .rss(items):        // Really Simple Syndication Feed Model
                return items.items?.map(FeedKitRSSItemDTO.init) ?? []
            case let .json(items):       // JSON Feed Model
                return []
            }
        case .failure(let error):
            return [] // handle error somehow
        }
    }
}
