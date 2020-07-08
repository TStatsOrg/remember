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
    private var subscribeAction: (() -> Void)? = nil
    private var unsubscribeAction: (() -> Void)? = nil
    
    public init(isSubscribed: Bool,
                subscribeAction:  (() -> Void)? = nil,
                unsubscribeAction:  (() -> Void)? = nil) {
        self.isSubscribed = isSubscribed
        self.subscribeAction = subscribeAction
        self.unsubscribeAction = unsubscribeAction
    }
    
    public var body: some View {
        VStack(alignment: .trailing) {
            if self.isSubscribed {
                Button(action: {
                    self.unsubscribeAction?()
                }) {
                    Text(Translations.AllFeeds.unsubscribeTitle)
                        .foregroundColor(Color.red)
                }
            } else {
                Button(action: {
                    self.subscribeAction?()
                }) {
                    Text(Translations.AllFeeds.subscribeTitle)
                        .foregroundColor(Color.green)
                }
            }
        }
    }
}
