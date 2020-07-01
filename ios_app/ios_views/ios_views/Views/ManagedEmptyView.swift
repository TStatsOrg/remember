//
//  ManagedEmptyView.swift
//  ios_views
//
//  Created by Gabriel Coman on 01/07/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import RememberShared

public struct ManagedEmptyView: View {
    
    let state: EmptyViewState
    
    public init(state: EmptyViewState) {
        self.state = state
    }
    
    public var body: some View {
        VStack(alignment: .center) {
            if state.isEmptyViewVisible {
                Image(systemName: state.emptyViewImage)
                    .resizable()
                    .colorMultiply(.secondary)
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 80, height: 80)
                    .padding(top: 20)
                Text(state.emptyViewText)
                    .foregroundColor(.secondary)
                Spacer()
            } else {
                EmptyView()
            }
        }
    }
}
