//
//  Colors.swift
//  ios_views
//
//  Created by Gabriel Coman on 30/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit

extension UIColor {
    
    /// Hex String is in format #RRGGBBAA
    public convenience init?(rgbaHex: String) {
        let red, green, blue, alpha: CGFloat

        if rgbaHex.hasPrefix("#") {
            let start = rgbaHex.index(rgbaHex.startIndex, offsetBy: 1)
            let hexColor = String(rgbaHex[start...])

            if hexColor.count == 8 {
                let scanner = Scanner(string: hexColor)
                var hexNumber: UInt64 = 0

                if scanner.scanHexInt64(&hexNumber) {
                    red = CGFloat((hexNumber & 0xff000000) >> 24) / 255
                    green = CGFloat((hexNumber & 0x00ff0000) >> 16) / 255
                    blue = CGFloat((hexNumber & 0x0000ff00) >> 8) / 255
                    alpha = CGFloat(hexNumber & 0x000000ff) / 255

                    self.init(red: red, green: green, blue: blue, alpha: alpha)
                    return
                }
            }
        }

        return nil
    }
}
