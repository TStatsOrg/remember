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
    
    @SwiftUI.State private var state: BookmarksViewState = BookmarksViewState()
    @SwiftUI.State private var isShowingSheet: Bool = false
    @SwiftUI.State private var search: String = ""
//    @SwiftUI.State private var selectedBookmark: Int32 = -1
//    @SwiftUI.State private var activeSheet: Int = 0
    
    public init() {}
    
    private func update(state: BookmarksState) {
//        if !isShowingSheet {
            self.state = BookmarksViewState(state: state)
//        }
        if let filterTopic = state.filterByTopic?.name {
            self.search = self.state.topicText(text: filterTopic)
            self.dismissKeyboard()
        }
    }
    
    public var body: some View {
        NavigationView {
            VStack {
                SearchView(viewModel: viewModel, binding: $search)
                NoSearchView(state: $state)
                List {
                    ForEach(state.bookmarksViewState, content: { state in
                        self.getCellType(state: state)
                            .editBookmarkActionSheetModifier {
                                self.navigation.showEditBookmark(bookmarkId: state.id)
                                self.isShowingSheet = true
//                                self.activeSheet = 1
//                                self.selectedBookmark = state.id
                            }
                    })
                }
                
            }
            .navigationBarTitle(Text(state.title), displayMode: .inline)
            .navigationBarItems(trailing: Button(action: {
                self.navigation.showTopicList()
                self.isShowingSheet = true
//                self.activeSheet = 2
            }, label: {
                Text("Topics")
            }))
            .onAppear {
                self.viewModel.observeBookmarkState(callback: self.update)
                self.viewModel.loadBookmarks()
            }
            .onDisappear {
                self.viewModel.cleanup()
            }
            .sheet(isPresented: $isShowingSheet) {
                self.navigateTo()
            }
        }
    }
    
    private func navigateTo() -> AnyView {
        switch self.navigation.destination {
        case .TopicList(let view):
            return view
        case .EditBookmark(let view):
            return view
        default:
            return AnyView(EmptyView())
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

public struct NoSearchView: View {
    
    @Binding var state: BookmarksViewState
    
    public var body: some View {
        VStack {
            if state.noSearchResults {
                Image(systemName: "nosign")
                    .resizable()
                    .colorMultiply(.secondary)
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 80, height: 80)
                    .padding(top: 20)
                Text("No results found")
                    .foregroundColor(.secondary)
                Spacer()
            } else {
                EmptyView()
            }
        }
    }
}

public struct SearchView: View {

    var bookmarksViewModel: MainHubViewModel
    @Binding var searchText: String

    public init(viewModel: MainHubViewModel, binding: Binding<String>) {
        bookmarksViewModel = viewModel
        _searchText = binding
    }

    public var body: some View {
        HStack {
            ManagedSearchView(binding: $searchText)
                .observeSearchChanged { term in
                    self.bookmarksViewModel.search(byName: term)
                }
            Button(action: {
                self.searchText = ""
                self.bookmarksViewModel.loadBookmarks()
                self.dismissKeyboard()
            }) {
                Text("Clear")
            }
            .padding(trailing: 12)
        }
    }
}
