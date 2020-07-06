//
//  WKWebViewProvider.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 06/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
import WebKit

public struct WebViewProvider {
    
    public let webView: WKWebView = WKWebView()
    
    public init() { /* n/a*/ }
    
    public func load(url: URL?) {
        if let url = url {
            webView.load(URLRequest(url: url))
        }
    }
    
    public func goBack() {
        if webView.canGoBack {
            webView.goBack()
        }
    }
    
    public func goForward() {
        if webView.canGoForward {
            webView.goForward()
        }
    }
}
