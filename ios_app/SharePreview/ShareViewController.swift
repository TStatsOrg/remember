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
import RememberShared
import ios_dependencies

class ShareViewController: UIViewController {
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        let capture = ExtensionContextDataCapture(withExtensionContext: self.extensionContext)
        let process = iOSDataProcess()
        capture.capture { (value) in
            let result = process.process(capture: value)
            print(result)
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
