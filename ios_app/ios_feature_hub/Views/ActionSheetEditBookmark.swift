//
//  ActionSheetEditBookmark.swift
//  ios_feature_hub
//
//  Created by Gabriel Coman on 09/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import ios_dependencies
import ios_views

func ActionSheetEditBookmark(
    editAction: (() -> Void)? = nil,
    deleteAction: (() -> Void)? = nil
) -> ActionSheet {
    
    return ActionSheet(title: Text("Options for bookmark"),
                       message: nil,
                       buttons: [
                        .default(Text("Edit"), action: editAction),
                        .destructive(Text("Delete"), action: deleteAction),
                        .cancel()
                    ])
}

struct EditBookmarkActionSheetModifier: ViewModifier {
    
    let editAction: () -> Void
    
    @State private var isShowingBottomSheet = false
    
    func body(content: Content) -> some View {
        content
            .onLongPressGesture {
                self.isShowingBottomSheet = true
            }
            .actionSheet(isPresented: $isShowingBottomSheet) {
                ActionSheetEditBookmark(editAction: self.editAction, deleteAction: {})
            }
    }
}

extension View {
    func editBookmarkActionSheetModifier(action: @escaping () -> Void) -> AnyView {
        return AnyView(self.modifier(EditBookmarkActionSheetModifier(editAction: action)))
    }
}
