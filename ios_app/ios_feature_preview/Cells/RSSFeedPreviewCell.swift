//
//  RSSFeedPreviewCell.swift
//  ios_feature_preview
//
//  Created by Gabriel Coman on 06/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
import ios_dependencies
import ios_views
import SDWebImage

class RSSFeedPreviewCell: UITableViewCell, GenericIdentifiable {
    
    @IBOutlet weak var feedIcon: UIImageView!
    @IBOutlet weak var feedTitle: UILabel!
    @IBOutlet weak var feedSource: UILabel!
    
    func redraw(viewState: BookmarkFeedViewState) {
        feedTitle.text = viewState.title
        feedSource.text = viewState.source
        feedIcon.sd_setImage(with: viewState.icon)
        feedIcon.isHidden = viewState.isIconHidden
    }
}
