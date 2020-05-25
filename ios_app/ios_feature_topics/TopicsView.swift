//
//  TopicsView.swift
//  ios_feature_topics
//
//  Created by Gabriel Coman on 29/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import RememberShared
import ios_dependencies
import ios_views

public struct TopicsView: View {
    
    @Injected private var viewModel: TopicsViewModel
    @Injected private var navigation: Navigation
    @State private var state: TopicsViewState = TopicsViewState()
    @State private var isShowingSheet: Bool = false
    @Environment(\.presentationMode) private var mode: Binding<PresentationMode>
    
    public init() {}
    
    public var body: some View {
        List(state.viewStates, rowContent: { content in
            VStack(alignment: .leading, spacing: 0) {
                Text(content.name)
                    .ActionButton()
                    .padding(bottom: 4.0)
                Text("\(content.noBookmarks) bookmarks")
                    .font(.caption)
                    .foregroundColor(.secondary)
            }
            .padding(bottom: 4.0)
            .onTapGesture {
                self.viewModel.filter(byTopic: content.topic)
                self.dismiss()
            }
            .editDeleteActionSheetModifier(
                title: "Change Topic",
                extraShowCondition: content.isEditable,
                editAction: {
                    self.navigation.showEditTopic(topicId: content.id)
                    self.isShowingSheet = true
                },
                deleteAction: self.viewModel.delete(topicId: content.id))
        })
        .navigationBarTitle(Text("Topics"))
        .navigationBarItems(trailing: Button(action: {
            self.navigation.showAddTopic()
            self.isShowingSheet = true
        }, label: {
            Text("Add")
        }))
        .onAppear {
            self.viewModel.observeTopicState(callback: self.update)
            self.viewModel.loadTopics()
        }
        .onDisappear {
            self.viewModel.cleanup()
        }
        .sheet(isPresented: $isShowingSheet, content: navigation.content)
    }
    
    private func update(state: TopicsState, bookmarks: [BookmarkState]) {
        self.state = TopicsViewState(state: state.topics, bookmarks: bookmarks)
    }
    
    private func dismiss() {
        mode.wrappedValue.dismiss()
    }
}
