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

class ShareViewController: UIViewController, PreviewViewModelDelegate {
    
    private lazy var viewModel: PreviewViewModel = {
        let vm = DependencyProvider.shared.getPreviewViewModel()
        vm.delegate = self
        return vm
    }()
    
    private lazy var capture: RawDataCapture = {
        return DependencyProvider.shared.getDataCapture(context: self.extensionContext)
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
        self.viewModel.save()
    }
    
    // PreviewViewModelDelegate
    
    func didSaveBookmark() {
        extensionContext?.completeRequest(returningItems: []) { (success) in
            
        }
    }
    
    struct ShareError: Error {
        let reason: String? = nil
    }
}
