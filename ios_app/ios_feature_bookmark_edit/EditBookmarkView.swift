//
//  EditBookmarkView.swift
//  ios_feature_bookmark_edit
//
//  Created by Gabriel Coman on 30/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import ios_dependencies
import RememberShared

public struct EditBookmarkView: View {
    
    @Injected var viewModel: EditBookmarkViewModel
    @Injected var navigation: Navigation
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    @SwiftUI.State var state: EditBookmarkViewState = EditBookmarkViewState()
    @SwiftUI.State private var isShowingTopics: Bool = false
    
    private let bookmarkId: Int32
    
    public init(bookmarkId: Int32) {
        self.bookmarkId = bookmarkId
    }
    
    public var body: some View {
        List(state.viewStates, rowContent: { content in
            Button(action: {
                self.viewModel.update(bookmark: self.bookmarkId, withTopic: content.id)
                self.viewModel.save()
            }) {
                Text(content.name)
                    .ActionButton()
                    .foregroundColor(content.isSelected ? Color.secondary : Color.primary)
                    .background(Color(content.isSelected ? .green : .clear))
            }
        })
        .onAppear {
            self.viewModel.loadEditableBookmark(forId: self.bookmarkId)
            self.viewModel.observeBookmarkSaved { (success) in
                self.mode.wrappedValue.dismiss()
            }
            self.viewModel.observeEditBookmarkState(callback: self.update)
        }
        .navigationBarTitle("Edit bookmark", displayMode: NavigationBarItem.TitleDisplayMode.inline)
        .navigationBarItems(trailing: Button(action: {
            self.isShowingTopics = true
        }, label: {
            Text("Topics")
        }))
        .sheet(isPresented: $isShowingTopics) {
            self.navigation.seeTopicsList()
        }
    }
    
    private func update(state: EditBookmarkState) {
        self.state = EditBookmarkViewState(state: state)
    }
}
