//
//  KeyboardUtils.swift
//  ios_views
//
//  Created by Gabriel Coman on 06/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
import SwiftUI

public extension View {
    
    func dismissKeyboard() {
        UIApplication.shared.endEditing()
    }
}

// extension for keyboard to dismiss
extension UIApplication {
    internal func endEditing() {
        sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}
