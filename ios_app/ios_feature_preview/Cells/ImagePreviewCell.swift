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
    @IBOutlet weak var infoLabel: UILabel!
    @IBOutlet weak var previewImage: UIImageView!
    
    func redraw(viewState: BookmarkImageViewState) {
        infoLabel.text = viewState.info
        previewImage.sd_setImage(with: viewState.url)
    }
}
