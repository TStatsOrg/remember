//
//  iOSDataCapture.swift
//  SharePreview
//
//  Created by Gabriel Coman on 13/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import Social
import RememberShared

class iOSDataCapture: NSObject, DataCapture {
    
    private let extensionContext: NSExtensionContext?
    
    private var capture: DataCaptureItem = DataCaptureItem.Unknown()
    
    init(withExtensionContext context: NSExtensionContext?) {
        extensionContext = context
    }
    
    func process(callback: @escaping (DataCaptureItem) -> Void) {
        // if context is empty - just callback w/ unknown
        guard let context = extensionContext else {
            callback(DataCaptureItem.Unknown())
            return
        }
        
        // if there is no extension item - just callback w/ unknown
        guard let extensionItem = context.inputItems.first as? NSExtensionItem else {
            callback(DataCaptureItem.Unknown())
            return
        }
        
        // if there is no item provider - just callback w/ unknown
        guard let itemProvider = extensionItem.attachments?.first else {
            callback(DataCaptureItem.Unknown())
            return
        }
        
        // the case where we have a simple Text
        if itemProvider.hasItemConformingToTypeIdentifier("public.text") {
            itemProvider.loadItem(forTypeIdentifier: "public.text") { (item, error) in
                
                // if there is an error loading the text item - just callback w/ unknown
                guard let textItem = item as? String else {
                    callback(DataCaptureItem.Unknown())
                    return
                }
                
                // finally, return a proper text item
                callback(DataCaptureItem.Text(text: textItem))
            }
        }
        
        // the case where we have a URL
        if itemProvider.hasItemConformingToTypeIdentifier("public.url") {
            itemProvider.loadItem(forTypeIdentifier: "public.url") { (item, error) in
                
                // if there is an error loading the url string - just callbacck w/ unknown
                guard let url = item as? URL else {
                    callback(DataCaptureItem.Unknown())
                    return
                }
                
                let urlItem = url.absoluteString
                
                // try to figure out if it's the URL of an image
                if urlItem.hasSuffix(".png") {
                    callback(DataCaptureItem.Image(url: urlItem))
                    return
                }
                
                // try to figure out if it's the URL of an image
                if urlItem.hasSuffix(".jpeg") {
                    callback(DataCaptureItem.Image(url: urlItem))
                    return
                }
                
                // try to figure out if it's the URL of an image
                if urlItem.hasSuffix(".jpg") {
                    callback(DataCaptureItem.Image(url: urlItem))
                    return
                }
                
                // if not, just return a normal text
                callback(DataCaptureItem.Text(text: urlItem))
            }
        }
    }
    
    func unbox() -> DataCaptureItem {
        return DataCaptureItem.Unknown()
    }
}
