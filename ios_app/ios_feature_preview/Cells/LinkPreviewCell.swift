//
//  LinkPreviewCell.swift
//  ios_feature_preview
//
//  Created by Gabriel Coman on 03/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
import ios_dependencies
import ios_views
import SDWebImage

class LinkPreviewCell: UITableViewCell, GenericIdentifiable {
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var infoLabel: UILabel!
    @IBOutlet weak var iconImage: UIImageView!
    
    func redraw(viewState: BookmarkLinkViewState) {
        titleLabel.text = viewState.title
        infoLabel.text = viewState.info
        iconImage.sd_setImage(with: viewState.icon)
        iconImage.isHidden = viewState.isIconHidden
    }
}
