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
    
    public func capture(input: Any?, data: @escaping (String?) -> Void) {
        guard let context = input as? NSExtensionContext else {
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
            itemProvider.loadItem(forTypeIdentifier: "public.text") { (item, _) in
                let textItem: String? = item as? String
                
                DispatchQueue.main.async {
                    data(textItem)
                }
            }
        }
        
        // the case where we have a URL
        if itemProvider.hasItemConformingToTypeIdentifier("public.url") {
            itemProvider.loadItem(forTypeIdentifier: "public.url") { (item, _) in
                
                let url: URL? = item as? URL
                let urlItem: String? = url?.absoluteString
                
                DispatchQueue.main.async {
                    data(urlItem)
                }
            }
        }
    }
}
