//
//  PreviewDataSource.swift
//  ios_feature_preview
//
//  Created by Gabriel Coman on 03/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
//import SharePreview
import ios_dependencies
import ios_views

class PreviewDataSource: NSObject {
    
    private var viewState: PreviewsViewState?
    
    func redraw(viewState: PreviewsViewState) {
        self.viewState = viewState
    }
}

extension PreviewDataSource: UITableViewDelegate {
    // N/A
}

extension PreviewDataSource: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewState?.viewStates.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let viewState = viewState?.viewStates.first?.viewState else {
            return UITableViewCell()
        }
        
        switch viewState {
        case let link as BookmarkLinkViewState:
            let cell = tableView.dequeueReusableCell(withIdentifier: LinkPreviewCell.identifier, for: indexPath) as! LinkPreviewCell
            cell.redraw(viewState: link)
            return cell
        case let text as BookmarkTextViewState:
            let cell = tableView.dequeueReusableCell(withIdentifier: TextPreviewCell.identifier, for: indexPath) as! TextPreviewCell
            cell.redraw(viewState: text)
            return cell
        case let image as BookmarkImageViewState:
            let cell = tableView.dequeueReusableCell(withIdentifier: ImagePreviewCell.identifier, for: indexPath) as! ImagePreviewCell
            cell.redraw(viewState: image)
            return cell
        case let feed as BookmarkFeedViewState:
            let cell = tableView.dequeueReusableCell(withIdentifier: FeedPreviewCell.identifier, for: indexPath) as! FeedPreviewCell
            cell.redraw(viewState: feed)
            return cell
        default:
            return UITableViewCell()
        }
    }
}
