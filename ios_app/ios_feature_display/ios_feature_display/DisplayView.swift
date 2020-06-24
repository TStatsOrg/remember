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
    @State private var url: URL?
    
    public init(rssItemId: Int32) {
        self.rssItemId = rssItemId
    }
    
    public var body: some View {
        ManagedWebView(url: url)
            .navigationBarTitle(Text("Display"), displayMode: .inline)
            .navigationBarItems(trailing: VStack {
                
                if (self.viewModel.isItemAleradyBookmarked(rssItemId: self.rssItemId)) {
                    Button(action: {
                        // self.viewModel.save()
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
                    if let item = $0.item {
                        let viewState = RSSItemViewState(state: item)
                        self.url = viewState.url
                    }
                }
                self.viewModel.loadRssItem(rssItemId: self.rssItemId)
            }
            .onDisappear {
                self.viewModel.cleanup()
            }
    }
}
