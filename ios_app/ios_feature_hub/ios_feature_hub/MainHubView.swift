//
//  MainHubView.swift
//  ios_feature_hub
//
//  Created by Gabriel Coman on 16/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import ios_views
import ios_dependencies
import RememberShared

public struct MainHubView: View {

    @Injected private var screenFactory: ScreenFactory
    @Injected private var viewModel: HubViewModel

    public init() { /* n/a */ }

    public var body: some View {
        TabView {
            screenFactory.buildUserFeedsScreen()
                .tabItem {
                    Image(systemName: "house.fill")
                    Text(Translations.MainHub.tab1Title)
                }
            screenFactory.buildBookmarksScreen()
                .tabItem {
                    Image(systemName: "bookmark.fill")
                    Text(Translations.MainHub.tab2Title)
                }
        }
        .navigationBarHidden(true)
        .onAppear {
            self.viewModel.loadData()
        }
    }
}
