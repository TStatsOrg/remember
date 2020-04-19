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
            List(state.viewStates, rowContent: self.getCellType)
            .navigationBarTitle(state.title)
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
            return AnyView(Text("M/A"))
        }
    }
}

struct TextBookmarkView: View {
    
    let viewState: BookmarkTextViewState
    
    var body: some View {
        Text(viewState.text)
    }
}

struct ImageBookmarkView: View {
    
    let viewState: BookmarkImageViewState
    
    var body: some View {
        Text(viewState.url?.absoluteString ?? "N/A Image")
    }
}

struct LinkBookmarkView: View {
    
    let viewState: BookmarkLinkViewState
    
    var body: some View {
        VStack {
            Text(viewState.title ?? "")
            Text(viewState.caption ?? "")
        }
    }
}
