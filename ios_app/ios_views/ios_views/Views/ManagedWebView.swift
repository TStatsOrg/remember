//
//  ManagedViewWiew.swift
//  ios_views
//
//  Created by Gabriel Coman on 22/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import WebKit

public struct ManagedWebView : UIViewRepresentable {
    
    private let url: URL?
    
    public init(url: URL?) {
        self.url = url 
    }
    
    public func makeUIView(context: Context) -> WKWebView  {
        return WKWebView()
    }
    
    public func updateUIView(_ uiView: WKWebView, context: Context) {
        if let url = url {
            uiView.load(URLRequest(url: url))
        }
    }
}
