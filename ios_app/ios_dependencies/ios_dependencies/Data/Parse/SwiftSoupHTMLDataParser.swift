//
//  SwiftSoupHTMLDataParse.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 04/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import RememberShared
import SwiftSoup

public class SwiftSoupHTMLDataParser: NSObject, HTMLDataParser {
    
    public func process(html: String) -> Either {
        do {
            let document: Document = try SwiftSoup.parse(html)
            let title = try? document.title()
            let description = try? document.head()?.select("meta[name=description]").first()?.attr("content")
            let iconImage = try? document.head()?.select("link[href~=.*\\.(ico|png)]").first()?.attr("href")
            let thumbnail1Image = try? document.body()?.select("link[rel=\"image_src\"]").first()?.attr("href")
            let thumbnial2Image = try? document.head()?.select("meta[property=og:image]").first()?.attr("content")

            return Either.Success(data: HTMLDataParserResult(title: title,
                                                             caption: description,
                                                             icon: thumbnial2Image ?? thumbnail1Image ?? iconImage))
        } catch {
            return Either.Failure(error: Errors.InvalidHTMLFormat())
        }
    }
}
