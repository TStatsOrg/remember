//
//  ManagedSubscribeButton.swift
//  ios_views
//
//  Created by Gabriel Coman on 28/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI

public struct ManagedSubscribeButton: View {
    
    private var isSubscribed: Bool
    private var subscribeAction: (() -> Void)?
    private var unsubscribeAction: (() -> Void)?
    
    public init(isSubscribed: Bool,
                subscribeAction:  (() -> Void)? = nil,
                unsubscribeAction:  (() -> Void)? = nil) {
        self.isSubscribed = isSubscribed
        self.subscribeAction = subscribeAction
        self.unsubscribeAction = unsubscribeAction
    }
    
    public var body: some View {
        VStack(alignment: .trailing) {
            Button(action: {
                if self.isSubscribed {
                    self.unsubscribeAction?()
                } else {
                    self.subscribeAction?()
                }
            }, label: {
                if self.isSubscribed {
                    Image(systemName: "bookmark.fill")
                } else {
                    Image(systemName: "bookmark")
                }
            })
        }
    }
}
