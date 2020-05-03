//
//  LinkPreviewCell.swift
//  ios_feature_preview
//
//  Created by Gabriel Coman on 03/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
import ios_dependencies

class LinkPreviewCell: UITableViewCell, GenericIdentifiable {
    
    @IBOutlet weak var sourceLabel: UILabel!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var dateLabel: UILabel!
    
    func redraw(viewState: PreviewLinkViewState) {
        sourceLabel.text = viewState.source
        titleLabel.text = viewState.title
        dateLabel.text = viewState.date
    }
}
