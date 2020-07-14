//
//  FeedKitFeedParse.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 11/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import FeedKit

public class FeedKitFeedDataParser: FeedDataParser {
    
    public func process(feed: String) -> Either {
        
        guard let data = feed.data(using: .utf8) else {
            return Either.Failure(error: Errors.InvalidFeedFormat())
        }
        
        let parser = FeedParser(data: data)
        let parseResult = parser.parse()
        
        switch parseResult {
        case .success(let feed):
            
            let link = feed.rssFeed?.link ?? feed.atomFeed?.links?.first?.attributes?.href
            var favico: String?
            if let favicoLink = link,
               let url = URL(string: favicoLink),
               let host = url.host,
               let scheme = url.scheme {
                favico = "\(scheme)://\(host)/favicon.ico"
            }
            
            let title = feed.rssFeed?.title ?? feed.atomFeed?.title
            let icon = favico ?? feed.rssFeed?.image?.link ?? feed.atomFeed?.icon
            let caption = feed.rssFeed?.description ?? feed.atomFeed?.subtitle?.value
            let update = feed.rssFeed?.lastBuildDate?.timeIntervalSince1970 ?? feed.atomFeed?.updated?.timeIntervalSince1970 ?? 0
            
            return Either.Success(data: FeedDataParserResult(title: title,
                                                             caption: caption,
                                                             icon: icon,
                                                             lastUpdate: Int64(update * 1000)))
        case .failure:
            return Either.Failure(error: Errors.InvalidFeedFormat())
        }
    }
}
