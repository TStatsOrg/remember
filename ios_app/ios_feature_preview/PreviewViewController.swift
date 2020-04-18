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
import ios_dependencies
import RememberShared

class PreviewViewController: UIViewController {
    
    @Injected var viewModel: PreviewViewModel
    @Injected var capture: RawDataCapture
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        viewModel.observePreviewState { (state) in
            print("State is \(state)")
        }
        
        viewModel.observeBookmarkSaved {
            self.extensionContext?.completeRequest(returningItems: [])
        }

        capture.capture(input: extensionContext) { (value) in
            self.viewModel.present(capturedRawData: value)
        }
    }
    
    @IBAction func didTapCancel(_ sender: Any) {
        extensionContext?.cancelRequest(withError: ShareError())
    }
    
    @IBAction func didTapSave(_ sender: Any) {
        self.viewModel.save()
    }
    
    struct ShareError: Error {
        let reason: String? = nil
    }
}
