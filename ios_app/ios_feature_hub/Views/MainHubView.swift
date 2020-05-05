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
    
    @Injected var bookmarksViewModel: MainHubViewModel
    @Injected var navigation: Navigation
    
    @SwiftUI.State private var state: BookmarksViewState = BookmarksViewState()
    @SwiftUI.State private var searchText : String = ""
    
    public init() {}
    
    private func update(state: BookmarksState) {
        self.state = BookmarksViewState(state: state)
    }
    
    public var body: some View {
        NavigationView {
            VStack {
                ManagedSearchView(text: $searchText)
                    .observeSearchOpened {
                        self.bookmarksViewModel.loadSuggestions()
                    }
                List {
                    ForEach(state.suggestionsViewState) { suggestion in
                        Text("\(suggestion.name)")
                    }
                    ForEach(state.bookmarksViewState, content: self.getCellType)
                }
            }
            .navigationBarTitle(Text(state.title), displayMode: NavigationBarItem.TitleDisplayMode.inline)
            .navigationBarItems(trailing: NavigationLink(destination: navigation.seeTopicsList(), label: {
                Text("Topics")
            }))
            .onAppear {
                self.bookmarksViewModel.observeBookmarkState(callback: self.update)
                self.bookmarksViewModel.loadBookmarks()
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
