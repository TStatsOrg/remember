//
//  WebKitUrlRetriever.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 06/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
import WebKit

/*
 * The URL resolver is here to take a base URL and resolve it through redirections, etc
 * until the final URL is reachedd, from which actual data can be safely extracted.
 */
protocol UrlResolver {
    func getFinalUrl(url: URL, callback: @escaping (URL) -> Void)
}

class WebKitUrlResolver: NSObject {
    
    private static let TIMEOUT = 1.5
    
    private let webView: WKWebView = WKWebView(frame: .zero)
    private var callback: ((URL) -> Void)?
    private var timer: Timer?
    
    private var isLoading: Bool = false
    
    private func prepareWebView(webView: WKWebView) {
        webView.navigationDelegate = self
        let viewController = getTopMostViewController()
        viewController?.view.addSubview(webView)
    }
    
    private func getTopMostViewController() -> UIViewController? {
        let keyWindow = UIApplication.shared.windows.first
        return keyWindow?.rootViewController
    }
}

extension WebKitUrlResolver: UrlResolver {
    
    func getFinalUrl(url: URL, callback: @escaping (URL) -> Void) {
        prepareWebView(webView: webView)
        webView.load(URLRequest(url: url))
        self.callback = callback
    }
}

extension WebKitUrlResolver: WKNavigationDelegate {
    
    func webView(_ webView: WKWebView, didStartProvisionalNavigation navigation: WKNavigation!) {
        isLoading = true
    }
    
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {

        isLoading = false
        
        timer?.invalidate()
        timer = Timer.scheduledTimer(withTimeInterval: WebKitUrlResolver.TIMEOUT, repeats: false) { _ in
            
            if !self.isLoading {
                if let url = webView.url {
                    self.callback?(url)
                }
            }
        }
    }
}
