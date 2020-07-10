//
//  PreviewDataSource.swift
//  ios_feature_preview
//
//  Created by Gabriel Coman on 03/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
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
        
        let cellFunc: (UITableView, IndexPath) -> UITableViewCell
        
        switch viewState {
        case let link as BookmarkLinkViewState:
            cellFunc = getLinkCell(state: link)
        case let text as BookmarkTextViewState:
            cellFunc = getTextCell(state: text)
        case let image as BookmarkImageViewState:
            cellFunc = getImageCell(state: image)
        case let feed as BookmarkFeedViewState:
            cellFunc = getFeedCell(state: feed)
        default:
            cellFunc = getDefaultCell()
        }
        
        return cellFunc(tableView, indexPath)
    }
}

extension PreviewDataSource {
    
    func getLinkCell(state: BookmarkLinkViewState) -> (UITableView, IndexPath) -> UITableViewCell {
        return { table, indexPath in
            
            guard let cell = table.dequeueReusableCell(withIdentifier: LinkPreviewCell.identifier,
                                                       for: indexPath) as? LinkPreviewCell else {
                return UITableViewCell()
            }
            
            cell.redraw(viewState: state)
            
            return cell
        }
    }
    
    func getTextCell(state: BookmarkTextViewState) -> (UITableView, IndexPath) -> UITableViewCell {
        return { table, indexPath in
            
            guard let cell = table.dequeueReusableCell(withIdentifier: TextPreviewCell.identifier,
                                                       for: indexPath) as? TextPreviewCell else {
                return UITableViewCell()
            }
            
            cell.redraw(viewState: state)
            
            return cell
        }
    }
    
    func getImageCell(state: BookmarkImageViewState) -> (UITableView, IndexPath) -> UITableViewCell {
        return { table, indexPath in
            
            guard let cell = table.dequeueReusableCell(withIdentifier: ImagePreviewCell.identifier,
                                                       for: indexPath) as? ImagePreviewCell else {
                return UITableViewCell()
            }
            
            cell.redraw(viewState: state)
            
            return cell
        }
    }
    
    func getFeedCell(state: BookmarkFeedViewState) -> (UITableView, IndexPath) -> UITableViewCell {
        return { table, indexPath in
            
            guard let cell = table.dequeueReusableCell(withIdentifier: FeedPreviewCell.identifier,
                                                       for: indexPath) as? FeedPreviewCell else {
                return UITableViewCell()
            }
            
            cell.redraw(viewState: state)
            
            return cell
        }
    }
    
    func getDefaultCell() -> (UITableView, IndexPath) -> UITableViewCell {
        return { _, _ in
            return UITableViewCell()
        }
    }
}
