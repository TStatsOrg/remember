//
//  Styles.swift
//  ios_views
//
//  Created by Gabriel Coman on 14/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI

public extension Text {
    
    func ActionButton() -> some View {
        return self
            .font(.footnote)
            .fontWeight(.bold)
            .foregroundColor(.accentColor)
    }
}
