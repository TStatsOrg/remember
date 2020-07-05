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
    
    public var body: some View {
        GeometryReader { geometry in
            ZStack(alignment: .leading) {
                Rectangle()
                    .foregroundColor(Color.white)
                    .frame(width: geometry.size.width , height: geometry.size.height)
                
                Rectangle()
                    .foregroundColor(Color(UIColor.systemBlue))
                    .frame(width: min(CGFloat(self.value)*geometry.size.width, geometry.size.width),
                           height: geometry.size.height)
                    .animation(.linear)
            }
        }
    }
}
