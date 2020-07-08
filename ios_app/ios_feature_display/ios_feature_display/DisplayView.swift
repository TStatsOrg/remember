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
    @Injected private var viewModel: DisplayViewModel
    @State private var progressValue: Float = 0.0
    @State private var state: DisplayViewState = DisplayViewState()
    @State private var currentUrl: URL? = nil
    @State private var isShowingSheet: Bool = false
    @Environment(\.presentationMode) private var mode: Binding<PresentationMode>
    
    public init(url: URL?) {
        self.url = url
    }
    
    public var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            if progressValue < 1.0 {
                ManagedProgressBar(value: $progressValue)
                    .frame(height: 2)
            }
            ManagedWebView(webView: provider.webView)
                .onStartLoading {
                    self.viewModel.startLoad()
                }
                .onProgrss { value in
                    self.progressValue = value
                }
                .onFinishedLoading { url, content in
                    self.currentUrl = url
                    self.viewModel.finishLoad(url: url.absoluteString, content: content)
                }
            HStack {
                Button(action: {
                    self.provider.goBack()
                }) {
                    Image(systemName: "arrowtriangle.left")
                }
                .frame(maxWidth: .infinity)
                
                Button(action: {
                    self.provider.goForward()
                }) {
                    Image(systemName: "arrowtriangle.right")
                }
                .frame(maxWidth: .infinity)
                
                Button(action: {
                    self.isShowingSheet = true
                }) {
                    Image(systemName: "square.and.arrow.up")
                }
                .frame(maxWidth: .infinity)
                
                Button(action: {
                    self.dismiss()
                }) {
                    Image(systemName: "xmark")
                }
                .frame(maxWidth: .infinity)
                
                Button(action: {
                    if self.state.isBookmarked {
                        self.viewModel.delete()
                    } else {
                        self.viewModel.save()
                    }
                }) {
                    if self.state.isBookmarked {
                        Image(systemName: "bookmark.fill")
                    } else {
                        Image(systemName: "bookmark")
                    }
                }
                .disabled(state.isDisabled)
                .frame(maxWidth: .infinity)
            }
            .frame(height: 52)
        }
        .navigationBarTitle(Text(state.title), displayMode: .inline)
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
        .sheet(isPresented: $isShowingSheet) {
            ManagedShareSheet(items: [self.currentUrl!])
        }
    }
    
    private func dismiss() {
        mode.wrappedValue.dismiss()
    }
}
