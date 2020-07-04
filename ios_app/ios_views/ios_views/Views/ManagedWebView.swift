//
//  ManagedViewWiew.swift
//  ios_views
//
//  Created by Gabriel Coman on 22/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import WebKit

public struct ManagedWebView {
    
    private let webView = WKWebView()
    
    private let delegate: ManagedWebViewDelegate?
    private let url: URL?
    
    public init(url: URL?) {
        self.url = url
        self.delegate = nil
    }
    
    public init(url: URL?, callback: @escaping (ManagedWebView.Result) -> Void) {
        self.url = url
        self.delegate = ManagedWebViewDelegate(withObserver: callback)
    }
    
    public struct Result {
        public let url: String
        public let content: String
    }
}

extension ManagedWebView {
    
    public func onFinishedLoading(callback: @escaping (ManagedWebView.Result) -> Void) -> some UIViewRepresentable {
        return ManagedWebView(url: url, callback: callback)
    }
}

extension ManagedWebView: UIViewRepresentable {
    
    public func makeUIView(context: Context) -> WKWebView  {
        return webView
    }
    
    public func updateUIView(_ uiView: WKWebView, context: Context) {
        if let url = url {
            uiView.navigationDelegate = delegate
            uiView.load(URLRequest(url: url))
        }
    }
}

class ManagedWebViewDelegate: NSObject, WKNavigationDelegate {
    
    private let navigationObserver: (ManagedWebView.Result) -> Void
    
    init(withObserver observer: @escaping (ManagedWebView.Result) -> Void) {
        navigationObserver = observer
    }
    
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        if let url = webView.url {
            webView.evaluateJavaScript("document.documentElement.outerHTML.toString()") { (result, error) in
                
                if error != nil {
                    print("GABBOX ==> Error getting HTML from \(url)")
                    return
                }
                
                if let content = result as? String {
                    self.navigationObserver(ManagedWebView.Result(url: url.absoluteString, content: content))
                }
            }
        }
    }
}
