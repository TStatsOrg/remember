//
//  DisplayView.swift
//  ios_feature_display
//
//  Created by Gabriel Coman on 22/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import ios_views
import ios_dependencies
import RememberShared

public struct DisplayView: View {
    
    private let rssItemId: Int32
    @Injected private var viewModel: DisplayViewModel
    @State private var state: DisplayViewState = DisplayViewState()
    @Environment(\.presentationMode) private var mode: Binding<PresentationMode>
    
    public init(rssItemId: Int32) {
        self.rssItemId = rssItemId
    }
    
    public var body: some View {
        ManagedWebView(url: state.url)
            .navigationBarTitle(Text(state.title), displayMode: .inline)
            .navigationBarItems(
                leading: Button(action: {
                    self.dismiss()
                }, label: {
                    Text(Translations.Display.doneTitle)
                }),
                trailing: VStack {
                
                if (self.state.isBookmarked) {
                    Button(action: {
                        self.viewModel.delete()
                    }, label: {
                        Image(systemName: "bookmark.fill")
                    })
                } else {
                    Button(action: {
                        self.viewModel.save()
                    }, label: {
                        Image(systemName: "bookmark")
                    })
                }
            })
            .onAppear {
                self.viewModel.observeDisplayState {
                    self.state = DisplayViewState(state: $0)
                }
                self.viewModel.loadRssItem(rssItemId: self.rssItemId)
            }
            .onDisappear {
                self.viewModel.cleanup()
            }
    }
    
    private func dismiss() {
        mode.wrappedValue.dismiss()
    }
}
