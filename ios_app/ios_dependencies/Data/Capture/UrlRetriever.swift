//
//  UrlRetriever.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 06/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation

class UrlRetriever {
    
    private let webKitRetriever = WebKitUrlRetriever()
    
    func getFinalUrl(url: URL) -> URL? {
        
        var finalUrl: URL? = nil
        let semaphore = DispatchSemaphore(value: 0)
        
        webKitRetriever.getFinalUrl(url: url) { (newUrl) in
            finalUrl = newUrl
            semaphore.signal()
        }
        
        _ = semaphore.wait(timeout: .distantFuture)
        
        return finalUrl
    }
}
