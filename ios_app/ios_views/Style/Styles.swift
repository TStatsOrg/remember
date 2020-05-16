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

public extension View {
    
    func padding(leading: CGFloat) -> some View {
        return self.padding(EdgeInsets(top: 0, leading: leading, bottom: 0, trailing: 0))
    }
    
    func padding(top: CGFloat) -> some View {
        return self.padding(EdgeInsets(top: top, leading: 0, bottom: 0, trailing: 0))
    }
    
    func padding(trailing: CGFloat) -> some View {
        return self.padding(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: trailing))
    }
    
    func padding(bottom: CGFloat) -> some View {
        return self.padding(EdgeInsets(top: 0, leading: 0, bottom: bottom, trailing: 0))
    }
}
