//
//  HTMLParser.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 06/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftSoup

struct HTMLParser {
    
    func parse(content: String) -> Result<Output, ParserError> {
        
        do {
            let document: Document = try SwiftSoup.parse(content)
            let title = try? document.title()
            let description = try? document.head()?.select("meta[name=description]").first()?.attr("content")
            let iconImage = try? document.head()?.select("link[href~=.*\\.(ico|png)]").first()?.attr("href")
            let thumbnail1Image = try? document.body()?.select("link[rel=\"image_src\"]").first()?.attr("href")
            let thumbnial2Image = try? document.head()?.select("meta[property=og:image]").first()?.attr("content")

            return .success(Output(title: title,
                                   description: description,
                                   icon: thumbnial2Image ?? thumbnail1Image ?? iconImage))
        } catch {
            return .failure(ParserError())
        }
    }
    
    struct Output {
        let title: String?
        let description: String?
        let icon: String?
    }
    
    struct ParserError: Error {}
}
