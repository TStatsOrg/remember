//
//  iOSRawDataProcess.swift
//  SharePreview
//
//  Created by Gabriel Coman on 13/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import SwiftSoup
import FeedKit

public class SystemDataProcess: RawDataProcess {
    
    private let downloader: UrlDownloader
    private let resolver: ContentResolver
    private let htmlParser: HTMLDataParser
    private let feedParser: FeedDataParser
    
    public init(downloader: UrlDownloader,
                resolver: ContentResolver,
                htmlParser: HTMLDataParser,
                feedParser: FeedDataParser) {
        self.downloader = downloader
        self.resolver = resolver
        self.htmlParser = htmlParser
        self.feedParser = feedParser
    }
    
    public func process(capture: String?, result: @escaping (RawDataProcessItem) -> Void) {
        
        // if null, it's unknown
        guard let content = capture else {
            result(RawDataProcessItem.Unknown())
            return
        }
        
        // if not url, must be text
        if !content.isUrl() {
            result(RawDataProcessItem.Text(text: content))
            return
        }
        
        // if it's an image, leave it at that
        if content.isImage() {
            result(RawDataProcessItem.Image(url: content))
            return
        }
        
        // try to conver it to a proper URL
        guard let url = URL(string: content) else {
            result(RawDataProcessItem.Unknown())
            return
        }
        
        downloader.download(url: url) { [weak self] (contentResult) in
            switch contentResult {
            case .success(let content):

                let parseResult = self?.feedParser.process(feed: content)
                
                if let parseResult = parseResult as? Either.Success, let feed = parseResult.data as? FeedDataParserResult {
                    result(RawDataProcessItem.Feed(url: url.absoluteString,
                                                   title: feed.title,
                                                   caption: feed.caption,
                                                   icon: feed.icon,
                                                   lastUpdate: feed.lastUpdate))
                } else {
                    self?.resolver.getContent(url: url) { [weak self] (finalURL, content) in
                        let htmlResult = self?.htmlParser.process(html: content)
                        
                        if let success = htmlResult as? Either.Success,
                           let successResult = success.data as? HTMLDataParserResult {
                            result(RawDataProcessItem.Link(url: finalURL.absoluteString,
                                                           title: successResult.title,
                                                           description: successResult.caption,
                                                           icon: successResult.icon))
                        }
                        if (htmlResult as? Either.Failure) != nil {
                            result(RawDataProcessItem.Unknown())
                        }
                    }
                }
            case .failure:
                print("GABBOX ==> Download failure")
            }
        }
    }
}

extension String {
    internal func isUrl() -> Bool {
        return self.hasPrefix("http://") || self.hasPrefix("https://")
    }
    
    internal func isImage() -> Bool {
        return self.hasSuffix(".jpg") || self.hasSuffix(".jpeg") || self.hasSuffix(".png")
    }
}
