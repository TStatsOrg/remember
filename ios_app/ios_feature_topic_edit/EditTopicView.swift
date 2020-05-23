//
//  EditTopicView.swift
//  ios_feature_topic_edit
//
//  Created by Gabriel Coman on 23/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import RememberShared
import ios_dependencies

public struct EditTopicView: View {
    
    @Injected private var viewModel: EditTopicViewModel
    @State private var state: String = ""
    @Environment(\.presentationMode) private var mode: Binding<PresentationMode>
    
    private let topicId: Int32
    
    public init(topicId: Int32) {
        self.topicId = topicId
    }
    
    public var body: some View {
        VStack(alignment: .leading) {
            TextField("Topic name", text: $state)
            Spacer()
        }
        .navigationBarTitle(Text("Edit topic"))
        .navigationBarItems(trailing:
            Button(action: {
                self.viewModel.updateTopic(topicId: self.topicId, name: self.state)
            }, label: {
                Text("Save")
            })
            .disabled(state.isEmpty)
        )
        .onAppear {
            self.viewModel.observeTopicUpdated { _ in
                self.mode.wrappedValue.dismiss()
            }
            self.viewModel.observeEditTopicState { state in
                self.state = state.topic.name
            }
            self.viewModel.loadEditableTopic(forTopicId: self.topicId)
        }
        .onDisappear {
            self.viewModel.cleanup()
        }
        .padding()
    }
}
