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
    
    @Injected var viewModel: TopicsViewModel
    @Injected var navigation: Navigation
    @SwiftUI.State var state: TopicsViewState = TopicsViewState()
    
    public init() {}
    
    public var body: some View {
        List(state.viewStates, rowContent: { content in
            Text("\(content.name)")
        })
        .navigationBarTitle(Text("Topics"))
        .navigationBarItems(trailing: NavigationLink(destination: navigation.seeAddTopic(), label: {
            Text("Add")
        }))
        .onAppear {
            self.viewModel.observeTopicState(callback: self.update)
            self.viewModel.loadTopics()
        }
    }
    
    private func update(state: TopicsState) {
        self.state = TopicsViewState(state: state.topics)
    }
}
