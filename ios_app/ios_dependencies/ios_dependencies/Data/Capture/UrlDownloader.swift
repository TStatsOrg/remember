//
//  UrlDownloader.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 06/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation

/*
 * The URL downloader downloads the contents of a URL, as a String
 */
public protocol UrlDownloader {
    func download(url: URL, callback: @escaping (Result<String, DownloaderError>) -> Void)
}

public struct SimpleUrlDownloader: UrlDownloader {
    
    public func download(url: URL, callback: @escaping (Result<String, DownloaderError>) -> Void) {
        DispatchQueue.global(qos: .default).async {
            
            let contents: String? = try? String(contentsOf: url)
            
            DispatchQueue.main.async {
                
                switch contents {
                case .some(let value):
                    callback(.success(value))
                default:
                    callback(.failure(DownloaderError()))
                }
            }
        }
    }
}

public struct DownloaderError: Error {}
