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
    @SwiftUI.State private var isShowingAddTopic: Bool = false
    
    private let bookmarkId: Int32
    
    public init(bookmarkId: Int32) {
        self.bookmarkId = bookmarkId
        print("MLogger: GABBOX2: Calling this for \(bookmarkId)")
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
                            .renderingMode(.original)
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(width: 20, height: 20)
                    } else {
                        Color(UIColor.white)
                            .frame(width: 20.0, height: 20.0)
                    }
                    Text(content.name)
                        .ActionButton()
                }
                
            }
        })
        .navigationBarTitle("Edit bookmark", displayMode: NavigationBarItem.TitleDisplayMode.inline)
        .navigationBarItems(trailing: Button(action: {
            self.isShowingAddTopic = true
        }, label: {
            Text("Add topic")
        }))
        .onAppear {
            self.viewModel.loadEditableBookmark(forId: self.bookmarkId)
            self.viewModel.observeBookmarkSaved { (success) in
                self.mode.wrappedValue.dismiss()
            }
            self.viewModel.observeEditBookmarkState(callback: self.update)
        }
        .onDisappear {
            self.viewModel.cleanup()
        }
        .sheet(isPresented: $isShowingAddTopic) {
            self.navigation.seeAddTopic()
        }
    }
    
    private func update(state: EditBookmarkState) {
        self.state = EditBookmarkViewState(state: state)
    }
}
