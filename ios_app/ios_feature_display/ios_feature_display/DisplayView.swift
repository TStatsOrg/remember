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
    
    private let url: URL?
    private let provider = WebViewProvider()
    @Injected private var viewModel: DisplayViewModel2
    @State private var state: DisplayViewState = DisplayViewState()
    @Environment(\.presentationMode) private var mode: Binding<PresentationMode>
    
    public init(url: URL?) {
        self.url = url
    }
    
    public var body: some View {
        ManagedWebView(webView: provider.webView)
            .onStartLoading {
                self.viewModel.startLoad()
            }
            .onFinishedLoading { result in
                self.viewModel.finishLoad(url: result.url, content: result.content)
            }
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
                    .disabled(state.isDisabled)
                } else {
                    Button(action: {
                        self.viewModel.save()
                    }, label: {
                        Image(systemName: "bookmark")
                    })
                    .disabled(state.isDisabled)
                }
            })
            .onAppear {
                self.viewModel.observeDisplayState {
                    self.state = DisplayViewState(state: $0)
                }
                self.viewModel.startLoad()
                self.provider.load(url: self.url)
            }
            .onDisappear {
                self.viewModel.cleanup()
            }
    }
    
    private func dismiss() {
        mode.wrappedValue.dismiss()
    }
}
