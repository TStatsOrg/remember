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

public class iOSDataProcess: RawDataProcess {
    
    private let resolver: UrlResolver
    private let downloader: UrlDownloader
    private let parser: HTMLParser
    
    init(resolver: UrlResolver,
         downloader: UrlDownloader,
         parser: HTMLParser) {
        self.resolver = resolver
        self.downloader = downloader
        self.parser = parser
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
        
        // get the final url, after all redirects
        resolver.getFinalUrl(url: url) { (finalUrl) in
            self.downloader.download(url: finalUrl) { (contentResult) in
                switch contentResult {
                case .success(let content):
                    let parsingResult = self.parser.parse(content: content)
                    
                    switch parsingResult {
                    case .success(let output):
                        result(RawDataProcessItem.Link(url: finalUrl.absoluteString,
                                                       title: output.title,
                                                       description: output.description,
                                                       icon: output.icon))
                    case .failure:
                        result(RawDataProcessItem.Unknown())
                    }
                    
                case .failure:
                    result(RawDataProcessItem.Unknown())
                }
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
