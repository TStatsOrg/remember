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
import Grid

public struct MainHubView: View {
    
    @Injected private var viewModel: MainHubViewModel
    @Injected private var navigation: Navigation
    @Injected private var deviceUtils: DeviceUtils
    @State private var state: BookmarksViewState = BookmarksViewState()
    @State private var isShowingSheet: Bool = false
    @State private var search: String = ""
    
    private var noGridTracks: Tracks {
        return deviceUtils.isTablet() ? 2 : 1
    }
    
    public init() {}
    
    private func update(state: BookmarksState) {
        self.state = BookmarksViewState(state: state)
        if self.state.isFilteringByTopic {
            self.search = self.state.searchTextWhenFilteringByTopic
            self.dismissKeyboard()
        }
    }
    
    public var body: some View {
        NavigationView {
            VStack {
                SearchView(viewModel: viewModel, binding: $search)
                NoSearchResultsView(state: $state)
                GetStartedView(state: $state)
                ScrollView {
                    Grid(state.bookmarksViewState) { state in
                        self.getCellType(state: state)
                        .editDeleteActionSheetModifier(
                            title: "Change Bookmark",
                            editAction: {
                                self.navigation.showEditBookmark(bookmarkId: state.id)
                                self.isShowingSheet = true
                            }, deleteAction: self.viewModel.delete(bookmarkId: state.id))
                    }
                    .gridStyle(
                        StaggeredGridStyle(.vertical, tracks: noGridTracks, spacing: 8)
                    )
                    .padding(EdgeInsets(top: 0, leading: 12, bottom: 0, trailing: 12))
                }
            }
            .navigationBarTitle(Text(state.title), displayMode: .inline)
            .navigationBarItems(trailing: Button(action: {
                self.navigation.showTopicList()
                self.isShowingSheet = true
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
            .onReceive(NotificationCenter.default.publisher(for: UIApplication.willEnterForegroundNotification)) { _ in
                self.viewModel.loadBookmarks()
            }
            .sheet(isPresented: $isShowingSheet, content: navigation.content)
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
