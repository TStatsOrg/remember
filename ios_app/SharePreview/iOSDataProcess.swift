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

class iOSDataProcess: NSObject, RawDataProcess {
    
    func process(capture: String?) -> RawDataProcessItem {
            
        // if null, it's unknown
        guard let content = capture else {
            return RawDataProcessItem.Unknown()
        }
        
        // if not url, must be text
        if !content.isUrl() {
            return RawDataProcessItem.Text(text: content)
        }
        
        // if it's image, leave it at that
        if content.isImage() {
            return RawDataProcessItem.Image(url: content)
        }
        
        // else, is a link, so get details
        guard let url = URL(string: content) else {
            return RawDataProcessItem.Unknown()
        }

        guard let contents = try? String(contentsOf: url) else {
            return RawDataProcessItem.Unknown()
        }

        do {
            let document: Document = try SwiftSoup.parse(contents)
            let title = try? document.title()
            let description = try? document.head()?.select("meta[name=description]").first()?.attr("content")
            let firstImage = try? document.head()?.select("link[href~=.*\\.(ico|png)]").first()?.attr("href")
            let secondImage = try? document.head()?.select("meta[itemprop=image]").first()?.attr("itemprop")
            let thirdImage = try? document.select("img").first()?.attr("href")

            return RawDataProcessItem.Link(url: content,
                                           title: title,
                                           description: description,
                                           icon: firstImage ?? secondImage ?? thirdImage)
        } catch {
            return RawDataProcessItem.Unknown()
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
