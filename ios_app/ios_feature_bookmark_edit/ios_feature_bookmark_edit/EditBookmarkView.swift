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
import ios_views
import RememberShared

public struct EditBookmarkView: View {
    
    @Injected private var viewModel: EditBookmarkViewModel
    @Injected private var navigation: Navigation
    @State private var state: EditBookmarkViewState = EditBookmarkViewState()
    @State private var isShowingSheet: Bool = false
    @Environment(\.presentationMode) private var mode: Binding<PresentationMode>
    
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
                HStack {
                    if content.isSelected {
                        Image(systemName: "checkmark")
                            .resizable()
                            .foregroundColor(.secondary)
                            .aspectRatio(contentMode: .fit)
                            .frame(width: 20, height: 20)
                    } else {
                        Color(.clear)
                            .frame(width: 20.0, height: 20.0)
                    }
                    Text(content.name)
                        .ActionButton()
                }
                
            }
        })
        .navigationBarTitle(Translations.EditBookmarks.title, displayMode: .inline)
        .navigationBarItems(trailing: Button(action: {
            self.navigation.showAddTopic()
            self.isShowingSheet = true
        }, label: {
            Text(Translations.EditBookmarks.addButtonTitle)
        }))
        .onAppear {
            self.viewModel.loadEditableBookmark(forId: self.bookmarkId)
            self.viewModel.observeBookmarkSaved { (success) in
                self.dismiss()
            }
            self.viewModel.observeEditBookmarkState(callback: self.update)
        }
        .onDisappear {
            self.viewModel.cleanup()
        }
        .sheet(isPresented: $isShowingSheet, content: navigation.content)
    }
    
    private func update(state: EditBookmarkState) {
        self.state = EditBookmarkViewState(state: state)
    }
    
    private func dismiss() {
        mode.wrappedValue.dismiss()
    }
}
