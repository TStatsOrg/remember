//
//  ContentResolver.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 06/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import WebKit
import UIKit

public protocol ContentResolver {
    func getContent(url: URL, callback: @escaping (URL, String) -> Void)
}

public class WebViewContentResolver: ContentResolver {
    
    private let webView: WKWebView
    private let provider: WebViewProvider
    private let delegate: WebViewDelegate
    
    public init(provider: WebViewProvider) {
        self.provider = provider
        self.webView = provider.webView
        self.delegate = WebViewDelegate()
        self.webView.navigationDelegate = self.delegate
    }
    
    private func getTopMostViewController() -> UIViewController? {
        let keyWindow = UIApplication.shared.windows.first
        return keyWindow?.rootViewController
    }
    
    public func getContent(url: URL, callback: @escaping (URL, String) -> Void) {
        delegate.finishNavigationObserver = callback
        
        let viewController = getTopMostViewController()
        viewController?.view.addSubview(webView)
        
        provider.load(url: url)
    }
}
