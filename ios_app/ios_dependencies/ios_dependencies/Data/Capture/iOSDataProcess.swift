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

public class iOSDataProcess: RawDataProcess {
    
    private let downloader: UrlDownloader
    private let resolver: ContentResolver
    private let dataProcess: HTMLDataProcess
    
    init(downloader: UrlDownloader,
         resolver: ContentResolver,
         dataProcess: HTMLDataProcess) {
        self.downloader = downloader
        self.resolver = resolver
        self.dataProcess = dataProcess
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
                if let data = content.data(using: .utf8) {
                    let parser = FeedParser(data: data)
                    let parseResult = parser.parse()
                    
                    switch parseResult {
                    case .success(let feed):
                        let title = feed.rssFeed?.title ?? feed.atomFeed?.title
                        let icon = feed.rssFeed?.image?.link ?? feed.atomFeed?.icon
                        let caption = feed.rssFeed?.description ?? feed.atomFeed?.subtitle?.value
                        
                        result(RawDataProcessItem.RSSFeed(url: url.absoluteString,
                                                          title: title,
                                                          caption: caption,
                                                          icon: icon))
                    case .failure:

                        self?.resolver.getContent(url: url) { [weak self] (finalURL, content) in
                            let htmlResult = self?.dataProcess.process(html: content)
                            
                            if let success = htmlResult as? Either.Success, let successResult = success.data as? HTMLDataProcessResult {
                                result(RawDataProcessItem.Link(url: finalURL.absoluteString,
                                                               title: successResult.title,
                                                               description: successResult.caption,
                                                               icon: successResult.icon))
                            }
                            if let error = htmlResult as? Either.Failure {
                                result(RawDataProcessItem.Unknown())
                            }
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
