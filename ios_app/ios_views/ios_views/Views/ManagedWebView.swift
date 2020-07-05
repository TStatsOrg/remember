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
    private let progress: ManagedWebViewProgress
    
    public init(webView: WKWebView) {
        self.webView = webView
        self.progress = ManagedWebViewProgress(webView: webView)
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
    
    public func onProgrss(callback: @escaping (Float) -> Void) -> ManagedWebView {
        progress.progressObserver = callback
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

public class ManagedWebViewProgress: NSObject {
    
    private let webView: WKWebView
    private var observation: NSKeyValueObservation? = nil
    internal var progressObserver: ((Float) -> Void)? = nil
    
    public init(webView: WKWebView) {
        self.webView = webView
    
        super.init()
    
        observation = self.webView.observe(\.estimatedProgress, options: [.new]) { [weak self] (_, _) in
            if let value = self?.webView.estimatedProgress {
                self?.progressObserver?(Float(value))
            }
        }
    }
    
    deinit {
        observation = nil
    }
}
