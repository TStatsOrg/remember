//
//  FeedView.swift
//  ios_feature_feed
//
//  Created by Gabriel Coman on 16/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import ios_views
import ios_dependencies

public struct FeedView: View {
    
    @Injected private var navigation: Navigation
    @State private var isShowingSheet: Bool = false
    
    public init() {
        
    }
    
    public var body: some View {
        Text("Feed")
            .navigationBarTitle(Text(Translations.Feed.title), displayMode: .inline)
            .navigationBarItems(trailing: Button(action: {
                self.navigation.showRSS()
                self.isShowingSheet = true
            }, label: {
                Text(Translations.Feed.feedButtonTitle)
            }))
            .sheet(isPresented: $isShowingSheet, content: navigation.content)
    }
}
