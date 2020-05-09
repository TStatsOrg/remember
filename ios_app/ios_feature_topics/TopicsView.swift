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
    
    public init() {}
    
    public var body: some View {
        List(state.viewStates, rowContent: { content in
            Text("\(content.name)")
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
        .sheet(isPresented: $isShowingAdd) {
            self.navigation.seeAddTopic()
        }
    }
    
    private func update(state: TopicsState) {
        self.state = TopicsViewState(state: state.topics)
    }
}
