//
//  ManagedProgressBar.swift
//  ios_views
//
//  Created by Gabriel Coman on 05/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI

public struct ManagedProgressBar: View {
    
    @Binding var value: Float
    
    public init(value: Binding<Float>) {
        self._value = value
    }
    
    private var foregroundBarColor: Color {
        if value >= 1.0 {
            return Color.white
        } else {
            return Color(UIColor.systemBlue)
        }
    }
    
    public var body: some View {
        GeometryReader { geometry in
            ZStack(alignment: .leading) {
                Rectangle()
                    .foregroundColor(Color.white)
                    .frame(width: geometry.size.width, height: geometry.size.height)
                
                Rectangle()
                    .foregroundColor(self.foregroundBarColor)
                    .frame(width: min(CGFloat(self.value)*geometry.size.width, geometry.size.width),
                           height: geometry.size.height)
                    .animation(.linear)
            }
        }
    }
}
