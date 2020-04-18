//
//  ContentView.swift
//  Remember
//
//  Created by Gabriel Coman on 11/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import ios_dependencies
import RememberShared
import Combine

struct BookmarksViewState {
    private let state: [BookmarkState]
    
    init(state: [BookmarkState] = []) {
        self.state = state
    }
    
    var title: String {
        return "You have \(state.count) bookmarks"
    }
    
    var viewStates: [BookmarkViewState] {
        return state.map { BookmarkViewState(state: $0) }
    }
}

struct BookmarkViewState: Identifiable {
    
    private let state: BookmarkState
    
    init(state: BookmarkState) {
        self.state = state
    }
    
    var id: Int32 {
        return state.id
    }
    
    var content: String {
        switch state {
        case let link as BookmarkStateLink:
            return link.url
        case let text as BookmarkStateText:
            return text.text
        case let image as BookmarkStateImage:
            return image.url
        default:
            return "N/A"
        }
    }
}

struct ContentView: View {
    
    @Injected var viewModel: MainHubViewModel
    @SwiftUI.State var state: BookmarksViewState = BookmarksViewState()
    
    private func update(state: [BookmarkState]) {
        self.state = BookmarksViewState(state: state)
    }
    
    var body: some View {
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
        ContentView()
    }
}
