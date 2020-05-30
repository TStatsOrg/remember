//
//  TextPreviewCell.swift
//  ios_feature_preview
//
//  Created by Gabriel Coman on 03/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
import ios_dependencies
import ios_views

class TextPreviewCell: UITableViewCell, GenericIdentifiable {
    @IBOutlet weak var textContentLabel: UILabel!
    @IBOutlet weak var infoLabel: UILabel!
 
    func redraw(viewState: BookmarkTextViewState) {
        infoLabel.text = viewState.info
        textContentLabel.text = viewState.text
    }
}
