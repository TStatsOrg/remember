//
//  EditDeleteBottomSheet.swift
//  ios_views
//
//  Created by Gabriel Coman on 23/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI

func EditDeleteBottomSheet(
    title: String,
    editAction: @escaping () -> Void,
    deleteAction: (() -> Void)? = nil
) -> ActionSheet {
    
    return ActionSheet(
        title: Text(title),
        message: nil,
        buttons: [
        .default(Text("Edit"), action: editAction),
        .destructive(Text("Delete"), action: deleteAction),
        .cancel()
        ]
    )
}

struct EditDeleteActionSheetModifier: ViewModifier {
    
    let title: String
    let editAction: () -> Void
    let deleteAction: () -> Void
    let extraShowCondition: Bool
    
    @State private var isShowing = false
    
    func body(content: Content) -> some View {
        content
            .onLongPressGesture {
                if self.extraShowCondition {
                    self.isShowing = true
                }
            }
            .actionSheet(isPresented: $isShowing) {
                EditDeleteBottomSheet(title: title,
                                      editAction: editAction,
                                      deleteAction: deleteAction)
            }
    }
}

public extension View {
    
    func editDeleteActionSheetModifier(
        title: String,
        extraShowCondition: Bool = true,
        editAction: @escaping () -> Void,
        deleteAction: @autoclosure @escaping () -> Void
    ) -> AnyView {
        return AnyView(
            self.modifier(EditDeleteActionSheetModifier(
                title: title,
                editAction: editAction,
                deleteAction: deleteAction,
                extraShowCondition: extraShowCondition))
        )
    }
    
}
