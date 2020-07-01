//
//  EmptyViewState.swift
//  ios_views
//
//  Created by Gabriel Coman on 01/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI

public struct EmptyViewState {
    let isEmptyViewVisible: Bool
    let emptyViewImage: String
    let emptyViewText: LocalizedStringKey
    
    public init(isVisible: Bool, image: String, text: LocalizedStringKey) {
        isEmptyViewVisible = isVisible
        emptyViewImage = image
        emptyViewText = text
    }
}
