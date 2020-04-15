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
    
    private lazy var viewModel: PreviewViewModel = {
        return DependencyProvider.getPreviewViewModel()
    }()
    
    private lazy var capture: RawDataCapture = {
        return DependencyProvider.getDataCapture(context: self.extensionContext)
    }()
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        viewModel.observePreviewState { (state) in
            print("State is \(state)")
        }
        
        capture.capture { (value) in
            self.viewModel.present(capturedRawData: value)
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
