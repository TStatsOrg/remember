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
    @SwiftUI.State private var isShowingTopics: Bool = false
    
    public init() {}
    
    private func update(state: BookmarksState) {
        self.state = BookmarksViewState(state: state)
    }
    
    public var body: some View {
        NavigationView {
            VStack {
                SearchView(viewModel: self.bookmarksViewModel)
                List {
                    ForEach(state.suggestionsViewState) { suggestion in
                        Button(action: {
                            self.dismissKeyboard()
                            self.bookmarksViewModel.filter(byTopic: suggestion.name)
                            self.bookmarksViewModel.clearSuggestions()
                        }) {
                            Text(suggestion.name).ActionButton()
                        }
                    }
                    ForEach(state.bookmarksViewState, content: { state in
                        self.getCellType(state: state)
                            .actionSheetModifier(viewState: state.viewState)
                    })
                }
            }
            .navigationBarTitle(Text(state.title), displayMode: .inline)
            .navigationBarItems(trailing: Button(action: {
                self.isShowingTopics = true
            }, label: {
                Text("Topics")
            }))
            .onAppear {
                self.bookmarksViewModel.observeBookmarkState(callback: self.update)
                self.bookmarksViewModel.loadBookmarks()
            }
            .sheet(isPresented: $isShowingTopics) {
                self.navigation.seeTopicsList()
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

public struct SearchView: View {
    
    var bookmarksViewModel: MainHubViewModel
    @SwiftUI.State private var isClearButton: Bool = false
    
    public init(viewModel: MainHubViewModel) {
        bookmarksViewModel = viewModel
    }
    
    public var body: some View {
        HStack {
            ManagedSearchView()
                .observeSearchOpened {
                    self.bookmarksViewModel.loadSuggestions()
                    self.isClearButton = true
                }
                .observeSearchChanged { term in
                    self.bookmarksViewModel.filterSuggestions(byName: term)
                    self.bookmarksViewModel.search(byName: term)
                }
                .observeCancelSearch {
                    self.bookmarksViewModel.loadSuggestions()
                }
                .observeSearchClosed {
                    self.isClearButton = false
                }
            
            if self.isClearButton {
                Button(action: {
                    self.dismissKeyboard()
                    self.bookmarksViewModel.loadBookmarks()
                }) {
                    Text("Clear")
                }
                .padding(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 12))
            }
        }
    }
}
