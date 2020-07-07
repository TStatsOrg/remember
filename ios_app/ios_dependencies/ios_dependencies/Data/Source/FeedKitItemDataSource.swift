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

public class FeedKitItemDataSource: FeedItemDataSource {
    
    public func getItems(fromLink: String) -> Either {
        
        guard let url = URL(string: fromLink) else {
            return Either.Failure(error: Errors.InvalidURL())
        }
        
        let parser = FeedParser(URL: url)
        
        let result = parser.parse()
        
        switch result {
        case .success(let feed):
            
            switch feed {
            // Atom Syndication Format Feed Model
            case let .atom(entries):
                let result = entries.entries?.map(FeeddKitAtomItemDTO.init) ?? []
                return Either.Success(data: result)
            // Really Simple Syndication Feed Model
            case let .rss(items):
                let result = items.items?.map(FeedKitRSSItemDTO.init) ?? []
                return Either.Success(data: result)
            // JSON Feed Model
            case .json:
                return Either.Failure(error: Errors.InvalidRSSFormat())
            }
        case .failure:
            return Either.Failure(error: Errors.InvalidRSSFormat())
        }
    }
}
