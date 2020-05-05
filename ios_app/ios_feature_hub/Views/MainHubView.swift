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
import SDWebImageSwiftUI
import ios_views

public struct MainHubView: View {
    
    @Injected var viewModel: MainHubViewModel
    @Injected var navigation: Navigation
    @SwiftUI.State var state: BookmarksViewState = BookmarksViewState()
    @SwiftUI.State private var searchText : String = ""
    
    public init() {}
    
    private func update(state: [BookmarkState]) {
        self.state = BookmarksViewState(state: state)
    }
    
    public var body: some View {
        NavigationView {
            VStack {
                ManagedSearchView(text: $searchText)
                List(state.viewStates, rowContent: self.getCellType)
            }
            .navigationBarTitle(Text(state.title), displayMode: NavigationBarItem.TitleDisplayMode.inline)
            .navigationBarItems(trailing: NavigationLink(destination: navigation.seeTopicsList(), label: {
                Text("Topics")
            }))
            .onAppear {
                self.viewModel.observeBookmarkState(callback: self.update)
                self.viewModel.loadBookmarks()
            }
        }
    }
    
    private func getCellType(state: BookmarkViewState) -> AnyView {
        guard let viewState = state.viewState else {
            return AnyView(Text("N/A"))
        }
        
        switch viewState {
        case let text as BookmarkTextViewState:
            return AnyView(TextBookmarkView(viewState: text))
        case let image as BookmarkImageViewState:
            return AnyView(ImageBookmarkView(viewState: image))
        case let link as BookmarkLinkViewState:
            return AnyView(LinkBookmarkView(viewState: link))
        default:
            return AnyView(Text("N/A"))
        }
    }
}
