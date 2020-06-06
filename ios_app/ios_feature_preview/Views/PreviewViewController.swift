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
    
    private let dataSource = PreviewDataSource()
    @Injected var viewModel: PreviewViewModel
    @Injected var capture: RawDataCapture
    @IBOutlet weak var mainTableView: UITableView!
    @IBOutlet weak var activityIndicator: UIActivityIndicatorView!
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        mainTableView.dataSource = dataSource
        mainTableView.delegate = dataSource
        
        viewModel.observePreviewState { (state) in
            let viewState = PreviewsViewState(state: state)
            self.dataSource.redraw(viewState: viewState)
            self.mainTableView.reloadData()
            self.activityIndicator.isHidden = viewState.isHidden
        }
        
        viewModel.observeBookmarkSaved { bookmarkId in
            self.extensionContext?.completeRequest(returningItems: [])
        }
        
        viewModel.clear()
        
        capture.capture(input: extensionContext) { (value) in
            self.viewModel.present(capturedRawData: "https://link.medium.com/W9eyD6WiW6")
//            self.viewModel.present(capturedRawData: value)
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        self.viewModel.cleanup()
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
