//
//  iOSDataProcess.swift
//  SharePreview
//
//  Created by Gabriel Coman on 13/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import SwiftSoup

class iOSDataProcess: NSObject, DataProcess {
    
    func process(capture: DataCaptureItem) -> DataProcessItem {

        switch capture {
        case _ as DataCaptureItem.Unknown:
            return DataProcessItem.Unknown()
        case let image as DataCaptureItem.Image:
            return DataProcessItem.Image(url: image.url)
        case let text as DataCaptureItem.Text:
            let content = text.text

            if !content.isUrl() {
                return DataProcessItem.Text(text: content)
            }

            guard let url = URL(string: content) else {
                return DataProcessItem.Unknown()
            }

            guard let contents = try? String(contentsOf: url) else {
                return DataProcessItem.Unknown()
            }

            do {
                let document: Document = try SwiftSoup.parse(contents)
                let title = try? document.title()
                let description = try? document.head()?.select("meta[name=description]").first()?.attr("content")
                let firstImage = try? document.head()?.select("link[href~=.*\\.(ico|png)]").first()?.attr("href")
                let secondImage = try? document.head()?.select("meta[itemprop=image]").first()?.attr("itemprop")
                let thirdImage = try? document.select("img").first()?.attr("href")

                return DataProcessItem.Link(url: content,
                                            title: title,
                                            description: description,
                                            icon: firstImage ?? secondImage ?? thirdImage)
            } catch {
                return DataProcessItem.Unknown()
            }
        default:
            return DataProcessItem.Unknown()
        }
    }
}

extension String {
    internal func isUrl() -> Bool {
        return self.hasPrefix("http://") || self.hasPrefix("https://")
    }
}
