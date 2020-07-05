//
//  ManagedViewWiew.swift
//  ios_views
//
//  Created by Gabriel Coman on 22/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import WebKit

public struct WebViewProvider {
    public let webView: WKWebView = WKWebView()
    
    public init() { /* n/a*/ }
    
    public func load(url: URL?) {
        if let url = url {
            webView.load(URLRequest(url: url))
        }
    }
}

public struct ManagedWebView {
    
    private let webView: WKWebView
    private let delegate: ManagedWebViewDelegate = ManagedWebViewDelegate()
    
    public init(webView: WKWebView) {
        self.webView = webView
        self.webView.navigationDelegate = delegate
    }
    
    public struct Result {
        public let url: String
        public let content: String
    }
}

extension ManagedWebView {
    
    public func onStartLoading(callback: @escaping () -> Void) -> ManagedWebView {
        delegate.startNavigationObserver = callback
        return self
    }
    
    public func onFinishedLoading(callback: @escaping (ManagedWebView.Result) -> Void) -> ManagedWebView {
        delegate.finishNavigationObserver = callback
        return self
    }
}

extension ManagedWebView: UIViewRepresentable {
    
    public func makeUIView(context: Context) -> WKWebView  {
        return webView
    }
    
    public func updateUIView(_ uiView: WKWebView, context: Context) {
        // n/a
    }
}

class ManagedWebViewDelegate: NSObject, WKNavigationDelegate {
    
    internal var startNavigationObserver: (() -> Void)?
    internal var finishNavigationObserver: ((ManagedWebView.Result) -> Void)?
    
    func webView(_ webView: WKWebView, didStartProvisionalNavigation navigation: WKNavigation!) {
        startNavigationObserver?()
    }
    
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        if let url = webView.url {
            webView.evaluateJavaScript("document.documentElement.outerHTML.toString()") { (result, error) in
                
                if error != nil {
                    print("GABBOX ==> Error getting HTML from \(url)")
                    return
                }
                
                if let content = result as? String {
                    self.finishNavigationObserver?(ManagedWebView.Result(url: url.absoluteString, content: content))
                }
            }
        }
    }
}
