//
//  ShareViewController.swift
//  SharePreview
//
//  Created by Gabriel Coman on 13/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
import Social
import MobileCoreServices

class ShareViewController: UIViewController {

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        let items = extensionContext?.inputItems
        let firstItem = items?.first as? NSExtensionItem
        let info = firstItem?.userInfo
        let provider: NSItemProvider? = firstItem?.attachments?.first
        
        print("TITLE: \(firstItem?.attributedTitle)")
        
        if provider?.hasItemConformingToTypeIdentifier("public.url") ?? false {
            provider?.loadItem(forTypeIdentifier: "public.url", completionHandler: { [weak self] (item, error) in
                print(item)
            })
        }
        
        if provider?.hasItemConformingToTypeIdentifier("public.text") ?? false {
            provider?.loadItem(forTypeIdentifier: "public.text", completionHandler: { [weak self] (item, error) in
                print(item)
            })
        }
    }
    
    @IBAction func didTapCancel(_ sender: Any) {
        extensionContext?.cancelRequest(withError: ShareError())
    }
    
    @IBAction func didTapSave(_ sender: Any) {
        extensionContext?.completeRequest(returningItems: [], completionHandler: { (success) in
            print("Success is \(success)")
        })
    }
    
    struct ShareError: Error {
        let reason: String? = nil
    }
    
}
