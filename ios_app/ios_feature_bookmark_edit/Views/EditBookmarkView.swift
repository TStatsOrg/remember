//
//  EditBookmarkView.swift
//  ios_feature_bookmark_edit
//
//  Created by Gabriel Coman on 30/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import ios_dependencies
import RememberShared

public struct EditBookmarkView: View {
    
    @Injected var viewModel: EditBookmarkViewModel
    private let bookmarkId: Int32
    
    public init(bookmarkId: Int32) {
        self.bookmarkId = bookmarkId
    }
    
    public var body: some View {
        Text("Edit bookmark")
            .onAppear {
                self.viewModel.loadEditableBookmark(forId: self.bookmarkId)
        }
    }
}
