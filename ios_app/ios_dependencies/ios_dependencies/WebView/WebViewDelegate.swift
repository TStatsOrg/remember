//
//  WebViewDelegate.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 06/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
import WebKit

public class WebViewDelegate: NSObject, WKNavigationDelegate {
    
    public var startNavigationObserver: (() -> Void)?
    public var finishNavigationObserver: ((URL, String) -> Void)?
    
    public func webView(_ webView: WKWebView, didStartProvisionalNavigation navigation: WKNavigation!) {
        startNavigationObserver?()
    }
    
    public func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        if let url = webView.url {
            webView.evaluateJavaScript("document.documentElement.outerHTML.toString()") { (result, error) in
                
                if error != nil {
                    print("GABBOX ==> Error getting HTML from \(url)")
                    return
                }
                
                if let content = result as? String {
                    self.finishNavigationObserver?(url, content)
                }
            }
        }
    }
}
