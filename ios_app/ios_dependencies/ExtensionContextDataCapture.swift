//
//  RawExtensionContextDataCapture.swift
//  SharePreview
//
//  Created by Gabriel Coman on 13/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import Social
import RememberShared

public class ExtensionContextDataCapture: NSObject, RawDataCapture {
    
    private let extensionContext: NSExtensionContext?
    
    public init(withExtensionContext context: NSExtensionContext?) {
        extensionContext = context
    }
    
    public func capture(data: @escaping (String?) -> Void) {
        // if context is empty - just callback w/ unknown
        guard let context = extensionContext else {
            data(nil)
            return
        }
        
        // if there is no extension item - just callback w/ unknown
        guard let extensionItem = context.inputItems.first as? NSExtensionItem else {
            data(nil)
            return
        }
        
        // if there is no item provider - just callback w/ unknown
        guard let itemProvider = extensionItem.attachments?.first else {
            data(nil)
            return
        }
        
        // the case where we have a simple Text
        if itemProvider.hasItemConformingToTypeIdentifier("public.text") {
            itemProvider.loadItem(forTypeIdentifier: "public.text") { (item, error) in
                let textItem: String? = item as? String
                
                DispatchQueue.main.async {
                    data(textItem)
                }
            }
        }
        
        // the case where we have a URL
        if itemProvider.hasItemConformingToTypeIdentifier("public.url") {
            itemProvider.loadItem(forTypeIdentifier: "public.url") { (item, error) in
                
                let url: URL? = item as? URL
                let urlItem: String? = url?.absoluteString
                
                DispatchQueue.main.async {
                    data(urlItem)
                }
            }
        }
    }
}
