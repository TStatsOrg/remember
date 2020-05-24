//
//  ForegroundNotification.swift
//  ios_views
//
//  Created by Gabriel Coman on 24/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI

public struct ForegroundNotification: ViewModifier {
    
    private let onForeground: () -> Void
    
    public init(onForeground foreground: @escaping () -> Void) {
        onForeground = foreground
    }
    
    public func body(content: Content) -> some View {
    	content
            .onReceive(NotificationCenter.default.publisher(for: UIApplication.willEnterForegroundNotification)) { _ in
                print("GABBOX => On foreground")
                self.onForeground()
            }
    }
}

public extension View {
    
    func onForeground(action: @escaping () -> Void) -> AnyView {
        return AnyView(self.modifier(ForegroundNotification(onForeground: action)))
    }
}
