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

public struct MainHubView: View {
    
    @Injected var viewModel: MainHubViewModel
    @Injected var navigation: Navigation
    @SwiftUI.State var state: BookmarksViewState = BookmarksViewState()
    
    public init() {}
    
    private func update(state: [BookmarkState]) {
        self.state = BookmarksViewState(state: state)
    }
    
    public var body: some View {
        NavigationView {
            List(state.viewStates, rowContent: self.getCellType)
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

struct TextBookmarkView: View {
    
    let viewState: BookmarkTextViewState
    
    var body: some View {
        VStack {
            Text(viewState.text)
            .padding()
        }
        .background(Color(UIColor.lightGray))
    }
}

struct ImageBookmarkView: View {
    
    let viewState: BookmarkImageViewState
    
    var body: some View {
        VStack {
            WebImage(url: self.viewState.url)
                .resizable()
                .frame(height: 300)
                .clipped()
        }
    }
}

struct LinkBookmarkView: View {
    
    let viewState: BookmarkLinkViewState
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(viewState.title ?? "")
                .fontWeight(.bold)
            Text(viewState.caption ?? "")
            Text(viewState.date)
                .fontWeight(.light)
        }
    }
}
