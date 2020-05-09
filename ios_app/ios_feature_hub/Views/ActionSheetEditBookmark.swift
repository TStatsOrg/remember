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

struct ActionSheetModifier: ViewModifier {
    
    @Injected private var navigation: Navigation
    @SwiftUI.State private var isShowingBottomSheet = false
    @SwiftUI.State private var isShowingEditBookmark: Bool = false
    
    let viewState: BookmarkViewStateType
    
    func body(content: Content) -> some View {
        content
            .onLongPressGesture {
                self.isShowingBottomSheet = true
            }
            .actionSheet(isPresented: $isShowingBottomSheet) {
                ActionSheetEditBookmark(editAction: { self.isShowingEditBookmark = true }, deleteAction: {})
            }
            .sheet(isPresented: $isShowingEditBookmark, content: {
                self.navigation.seeEditBookmark(forBookmarkId: self.viewState.id)
            })
    }
}

extension View {
    func actionSheetModifier(viewState: BookmarkViewStateType?) -> AnyView {
        guard let viewState = viewState else {
            return AnyView(self)
        }
        
        return AnyView(self.modifier(ActionSheetModifier(viewState: viewState)))
    }
}
