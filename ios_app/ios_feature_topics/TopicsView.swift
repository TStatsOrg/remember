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

public struct TopicsView: View {
    
    @Injected private var viewModel: TopicsViewModel
    @Injected private var navigation: Navigation
    @SwiftUI.State private var state: TopicsViewState = TopicsViewState()
    @SwiftUI.State private var isShowingAdd: Bool = false
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    public init() {}
    
    public var body: some View {
        List(state.viewStates, rowContent: { content in
            Button(action: {
                self.viewModel.filter(byTopic: content.topic)
                self.dismiss()
            }) {
                VStack(alignment: .leading, spacing: 0) {
                    Text(content.name)
                        .ActionButton()
                        .padding(bottom: 4.0)
                    
                    Text("12 bookmarks")
                        .font(.caption)
                        .foregroundColor(.secondary)
                }
                .padding(bottom: 4.0)
            }
        })
        .navigationBarTitle(Text("Topics"))
        .navigationBarItems(trailing: Button(action: {
            self.isShowingAdd = true
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
        .sheet(isPresented: $isShowingAdd) {
            self.navigation.seeAddTopic()
        }
    }
    
    private func update(state: TopicsState) {
        self.state = TopicsViewState(state: state.topics)
    }
    
    private func dismiss() {
        mode.wrappedValue.dismiss()
    }
}
