//
//  NoSearchResultsView.swift
//  ios_feature_hub
//
//  Created by Gabriel Coman on 24/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import RememberShared
import ios_views

public struct NoSearchResultsView: View {
    
    @Binding var state: BookmarksViewState
    
    public var body: some View {
        VStack {
            if state.noSearchResults {
                Image(systemName: "nosign")
                    .resizable()
                    .foregroundColor(.secondary)
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 80, height: 80)
                    .padding(top: 20)
                Text("No results found")
                    .foregroundColor(.secondary)
                Spacer()
            } else {
                EmptyView()
            }
        }
    }
}
