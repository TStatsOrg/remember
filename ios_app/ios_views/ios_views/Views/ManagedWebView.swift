//
//  ManagedViewWiew.swift
//  ios_views
//
//  Created by Gabriel Coman on 22/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import WebKit
import ios_dependencies

public struct ManagedWebView {
    
    private let webView: WKWebView
    private let delegate: WebViewDelegate = WebViewDelegate()
    private let progress: WebViewProgress
    
    public init(webView: WKWebView) {
        self.webView = webView
        self.progress = WebViewProgress(webView: webView)
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
    
    public func onFinishedLoading(callback: @escaping (URL, String) -> Void) -> ManagedWebView {
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
