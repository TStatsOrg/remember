//
//  AddTopicView.swift
//  ios_feature_topic_add
//
//  Created by Gabriel Coman on 29/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import RememberShared
import ios_dependencies

public struct AddTopicView: View {
    
    @Injected var viewModel: AddTopicViewModel
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    @SwiftUI.State var state: String = ""
    
    public init() {}
    
    public var body: some View {
        VStack(alignment: .leading) {
            TextField("New topic name", text: $state)
            Spacer()
        }
        .navigationBarTitle(Text("New topic"))
        .navigationBarItems(trailing:
            Button(action: {
                self.viewModel.addTopic(name: self.state)
            }, label: {
                Text("Add")
            })
        )
        .onAppear {
            self.viewModel.observeTopicSaved(callback: self.dismiss)
        }
        .onDisappear {
            self.viewModel.cleanup()
        }
        .padding()
    }
    
    private func dismiss(boolean: KotlinBoolean) {
        mode.wrappedValue.dismiss()
    }
}
