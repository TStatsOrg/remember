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
    @IBOutlet weak var sourceLabel: UILabel!
    @IBOutlet weak var textContentLabel: UILabel!
    @IBOutlet weak var dateLabel: UILabel!
 
    func redraw(viewState: BookmarkTextViewState) {
        sourceLabel.text = viewState.source
        dateLabel.text = viewState.date
        textContentLabel.text = viewState.text
    }
}
