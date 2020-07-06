//
//  WebViewProgress.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 06/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
import WebKit

public class WebViewProgress: NSObject {
    
    private let webView: WKWebView
    private var observation: NSKeyValueObservation? = nil
    
    public var progressObserver: ((Float) -> Void)? = nil
    
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

