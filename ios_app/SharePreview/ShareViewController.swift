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

class ShareViewController: UIViewController {

    private lazy var capture = {
        return iOSDataCapture(withExtensionContext: self.extensionContext)
    }()
    
    private lazy var process = {
        return iOSDataProcess()
    }()
    
    private lazy var captureViewModel = {
        return SharedDataCaptureViewModel(dataCapture: capture, dataProcess: process)
    }()
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        capture.process { item in
            self.captureViewModel.capture { processedItem in
                
            }
        }
        
        let capture = iOSDataCapture(withExtensionContext: extensionContext)
        let process = iOSDataProcess()
        capture.process { (item) in
            process.process(capture: item)
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
