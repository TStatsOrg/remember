//
//  ImagePreviewCell.swift
//  ios_feature_preview
//
//  Created by Gabriel Coman on 03/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
import ios_dependencies
import ios_views
import SDWebImage

class ImagePreviewCell: UITableViewCell, GenericIdentifiable {
    @IBOutlet weak var sourceLabel: UILabel!
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var previewImage: UIImageView!
    
    func redraw(viewState: BookmarkImageViewState) {
        sourceLabel.text = viewState.source
        dateLabel.text = viewState.date
        previewImage.sd_setImage(with: viewState.url)
    }
}
