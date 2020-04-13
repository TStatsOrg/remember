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

class iOSDataProcess: NSObject {
    
    func process(item: DataCaptureItem) {
        switch item {
        case _ as DataCaptureItem.Unknown:
            let item = DataProcessItem.Unknown()
            print(item)
        case let image as DataCaptureItem.Image:
            let item = DataProcessItem.Image(url: image.url)
            print(item)
        case let text as DataCaptureItem.Text:
            let content = text.text
            
            if content.hasPrefix("http://") || content.hasPrefix("https://") {
                
                guard let url = URL(string: content) else {
                    return
                }
                
                let task = URLSession.shared.dataTask(with: url) { (data, response, error) in
                    
                    guard let data = data, let contents = String(data: data, encoding: .utf8) else {
                        return
                    }
                    
                    do {
                        let document: Document = try SwiftSoup.parse(contents)
                        let title = try document.title()
                        let description = try document.head()?.select("meta[name=description]").first()?.attr("content")
                        let firstImage = try document.head()?.select("link[href~=.*\\.(ico|png)]").first()?.attr("href")
                        let secondImage = try document.head()?.select("meta[itemprop=image]").first()?.attr("itemprop")
                        let thirdImage = try document.select("img").first()?.attr("href")
                        
                        let item = DataProcessItem.Link(url: content,
                                                        title: title,
                                                        description: description,
                                                        icon: firstImage ?? secondImage ?? thirdImage)
                        
                        print(item)
                    } catch Exception.Error(_, let message) {
                        print(message)
                    } catch {
                        print("Unknown error")
                    }
                }
                
                task.resume()
            } else {
                let item = DataProcessItem.Text(text: content)
                print(item)
            }
        default:
            break
        }
    }
}
