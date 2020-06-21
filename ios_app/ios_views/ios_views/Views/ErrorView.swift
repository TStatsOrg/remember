//
//  ErrorView.swift
//  ios_views
//
//  Created by Gabriel Coman on 21/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI

public struct ErrorView: View {
    
    private let state: ErrorViewState
    
    public init(error: ErrorViewState) {
        self.state = error
    }
    
    public var body: some View {
        HStack {
            VStack(alignment: .center) {
                Image(systemName: "xmark.octagon")
                    .resizable()
                    .foregroundColor(.red)
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 80, height: 80)
                    .padding(top: 20)
                Text(state.message)
                    .font(.body)
                    .foregroundColor(.secondary)
            }
        }
        .frame(
            maxWidth: .infinity,
            maxHeight: .infinity,
            alignment: .center
        )
    }
}
