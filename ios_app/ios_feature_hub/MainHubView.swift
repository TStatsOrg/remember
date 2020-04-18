//
//  MainHubView.swift
//  ios_feature_hub
//
//  Created by Gabriel Coman on 18/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import ios_dependencies
import RememberShared

public struct MainHubView: View {
    
    @Injected var viewModel: MainHubViewModel
    @SwiftUI.State var state: BookmarksViewState = BookmarksViewState()
    
    public init() {}
    
    private func update(state: [BookmarkState]) {
        self.state = BookmarksViewState(state: state)
    }
    
    public var body: some View {
        NavigationView {
            List(state.viewStates) { viewState in
                Text(viewState.content)
            }
            .navigationBarTitle(state.title)
            .onAppear {
                self.viewModel.observeBookmarkState(callback: self.update)
                self.viewModel.loadBookmarks()
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        MainHubView()
    }
}
